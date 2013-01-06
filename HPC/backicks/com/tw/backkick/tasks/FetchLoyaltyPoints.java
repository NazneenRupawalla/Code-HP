package com.tw.backkick.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.thoughtworks.hp.R;

public class FetchLoyaltyPoints extends AsyncTask<String, String, String> {
    public static final String URL = "http://backkick-service.herokuapp.com/customers/loyalty_points?customer_id=2";
    private ProgressDialog progressDialog;
    private Context context;

    public FetchLoyaltyPoints(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Loyalty Points", "Fetching points...", true);
    }

    @Override
    protected String doInBackground(String... strings) {

        String points = "0";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            points = convertStreamToString(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        setLoyaltyPoints(result);
        progressDialog.dismiss();
    }

    private void setLoyaltyPoints(String result) {
        Activity activity = (Activity) context;
        TextView loyaltyPoints = (TextView) activity.findViewById(R.id.loyalty_points);
        TextView loyaltyPointsForSC = (TextView) activity.findViewById(R.id.loyalty_points_sc);
        loyaltyPoints.setText("200");
        loyaltyPointsForSC.setText(result);
    }

}
