package com.tw.backkick.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.thoughtworks.hp.R;
import com.tw.backkick.ImageChangeListener;
import com.tw.backkick.twitter.PrepareRequestTokenActivity;
import com.tw.backkick.twitter.TwitterUtils;

public class PagerActivity extends Activity {

    private ViewPager awesomePager;
    private static int NUM_AWESOME_VIEWS = 0;
    private SharedPreferences prefs;

    private AwesomePagerAdapter awesomeAdapter;
    private Map<Integer, String> imageNameMap = new HashMap<Integer, String>();
    private final Handler mTwitterHandler = new Handler();
    private ImageChangeListener listener;
    private static final String APP_ID = "165131880277343";
    private static final String[] PERMISSIONS = new String[]{"publish_stream"};

    private static final String TOKEN = "access_token";
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "facebook-credentials";

    private Facebook facebook;
    private String messageToPost;
    AsyncFacebookRunner asyncFacebookRunner = new AsyncFacebookRunner(facebook);

    final Runnable mUpdateTwitterNotification = new Runnable() {
        public void run() {
            Toast.makeText(getBaseContext(), "Tweeted !", Toast.LENGTH_LONG).show();
        }
    };

    @SuppressLint({ "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);
        awesomeAdapter = new AwesomePagerAdapter(this, getStoredImages());
        awesomePager = (ViewPager) findViewById(R.id.awesomepager);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        facebook = new Facebook(APP_ID);
        String facebookMessage = getIntent().getStringExtra("facebookMessage");
        if (facebookMessage == null) {
            facebookMessage = "Test wall post";
        }
        messageToPost = facebookMessage;
        awesomePager.setAdapter(awesomeAdapter);
        listener = new ImageChangeListener(imageNameMap);
        awesomePager.setOnPageChangeListener(listener);
        enableTwitter();
        enableFacebook();
    }

    private void enableFacebook() {
        ImageButton fbButton = (ImageButton) findViewById(R.id.fb_btn);
        fbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!facebook.isSessionValid()) {
                    loginAndPostToWall(image());
                } else {
                    try {
                        postToWall(messageToPost);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

            }
        });
    }

    private void enableTwitter() {
        ImageButton tweetButton = (ImageButton) findViewById(R.id.tweet_btn);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (TwitterUtils.isAuthenticated(prefs)) {
                    sendTweet();
                } else {
                    Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
                    i.putExtra("tweet_msg", getTweetMsg());
                    i.putExtra("image_name", image());
                    startActivity(i);
                }
            }
        });
    }

    private Bitmap[] getStoredImages() {
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = path.listFiles();
        Bitmap[] bitmaps = new Bitmap[files.length];
        for (int i = 0; i < files.length; i++) {
            String absolutePath = files[i].getAbsolutePath();
            bitmaps[i] = BitmapFactory.decodeFile(absolutePath);
            imageNameMap.put(i, absolutePath);
        }
        NUM_AWESOME_VIEWS = files.length;
        return bitmaps;
    }

    private class AwesomePagerAdapter extends PagerAdapter {

        private Context context;
        private Bitmap[] bitmaps;

        private AwesomePagerAdapter(Context context, Bitmap[] bitmaps) {
            this.context = context;
            this.bitmaps = bitmaps;
        }

        public int getCount() {
            return NUM_AWESOME_VIEWS;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(this.bitmaps[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            collection.addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            ((ViewPager) collection).removeView((ImageView) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == ((ImageView) o);
        }
    }

    public void sendTweet() {
        Thread t = new Thread() {
            public void run() {
                try {
                    TwitterUtils.sendTweet(prefs, getTweetMsg(), image());
                    mTwitterHandler.post(mUpdateTwitterNotification);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        };
        t.start();
    }

    private String image() {
        return listener.getFileName();
    }

    private String getTweetMsg() {
        return "Get discounts @ some_retail_shop by redeeming this coupon";
    }


    public void loginAndPostToWall(String image) {
        facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
    }

    public void postToWall(String image) throws IOException {

        byte[] data = null;
        Bitmap bmp = BitmapFactory.decodeFile(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        data = baos.toByteArray();

        Bundle params = new Bundle();
        params.putString(Facebook.TOKEN, facebook.getAccessToken());
        params.putString("message", getTweetMsg());
        params.putByteArray("source", data);

        try {
            facebook.request("me");
            String response = facebook.request("me/photos", params, "POST");
            Log.d("Tests", "got response: " + response);
            if (response == null || response.equals("") ||
                    response.equals("false")) {
                showToast("Blank response.");
            } else {
                showToast("Message posted to your facebook wall!");
            }
            finish();
        } catch (Exception e) {
            showToast("Failed to post to wall!");
            e.printStackTrace();
            finish();
        }
    }

    class LoginDialogListener implements Facebook.DialogListener {
        public void onComplete(Bundle values) {
            if (messageToPost != null) {
                try {
                    postToWall(image());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onFacebookError(FacebookError error) {
            showToast("Authentication with Facebook failed!");
            finish();
        }

        public void onError(DialogError error) {
            showToast("Authentication with Facebook failed!");
            finish();
        }

        public void onCancel() {
            showToast("Authentication with Facebook cancelled!");
            finish();
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }


}
