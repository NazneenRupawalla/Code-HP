package com.tw.tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.tw.model.Coupon;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostAsyncTask extends AsyncTask<String, String, String> {

    public static final String POST_URL = "http://backkick-service.herokuapp.com/customers/update_points";
    private Coupon coupon;

    public PostAsyncTask(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(POST_URL);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("customer_id", String.valueOf(coupon.getCustomerId())));
            nameValuePairs.add(new BasicNameValuePair("coupon_id", String.valueOf(coupon.getId())));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return null;
    }
}
