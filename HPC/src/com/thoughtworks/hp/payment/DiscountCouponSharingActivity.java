package com.thoughtworks.hp.payment;



import org.json.JSONObject;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.thoughtworks.hp.R;

import com.thoughtworks.hp.facebook.integration.SessionStore;
import com.thoughtworks.hp.facebook.integration.Util;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;



public class DiscountCouponSharingActivity extends Activity implements OnClickListener{

	private Facebook mFacebook=new Facebook("274586005984896");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_on_fb_wall);
		Button post=(Button)findViewById(R.id.post_button);
		post.setOnClickListener(this);

	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
        mFacebook.authorizeCallback(requestCode, resultCode, data);
        setResult(1);
		this.finish();
    	}

	//private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			if( !mFacebook.isSessionValid() ) {
				Toast.makeText(DiscountCouponSharingActivity.this, "Authorizing", Toast.LENGTH_SHORT).show();
				final String[] PERMISSIONS =
						new String[] {"publish_stream", "read_stream", "offline_access"};


				mFacebook.authorize(DiscountCouponSharingActivity.this, PERMISSIONS, new LoginDialogListener());
				
			}
			else {
				try {
					JSONObject json = Util.parseJson(mFacebook.request("me"));
					String facebookID = json.getString("id");
					String firstName = json.getString("first_name");
					String lastName = json.getString("last_name");
				}
				catch( Exception error ) {
					Toast.makeText(DiscountCouponSharingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
				}

			}
		}
	//};





public final class LoginDialogListener implements DialogListener {

	public void onComplete(Bundle values) {
		try {

			//The user has logged in, so now you can query and use their Facebook info
			JSONObject json = Util.parseJson(mFacebook.request("me"));
			String facebookID = json.getString("id");
			String firstName = json.getString("first_name");
			String lastName = json.getString("last_name");
			SessionStore.save(mFacebook, DiscountCouponSharingActivity.this);
			Bundle params = new Bundle();
			params.putString("message", firstName+" has just shopped at HyperCity and she is distributing discount coupons");
			params.putString("link", "www.hypercityindia.com");
			params.putString("name", "5% Discount Coupon");
			params.putString("caption", "Grab the opportunity..HURRY!!!!");
			params.putString("description", "Enjoy Shopping..Share with your friends and get Cash Back");
			params.putString("picture", " http://media.tkcarsites.com/globalmedia/Generic/Images/Specials/Percent_Off_5.png");
			String response = mFacebook.request("me/feed",params,"POST");
			if (response == null || response.equals("") || 
					response.equals("false")) {
				Log.v("Error", "Blank response");
			}	
			
		}
		catch( Exception error ) {
			Toast.makeText(DiscountCouponSharingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onFacebookError(FacebookError e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(DialogError e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}
}

}



