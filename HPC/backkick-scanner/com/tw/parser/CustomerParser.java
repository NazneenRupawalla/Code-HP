package com.tw.parser;

import android.util.Log;
import com.tw.model.Coupon;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerParser {

    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String COUPON_ID = "coupon_id";

    public static Coupon parseResponse(String responseJSON) {
        try {
            JSONObject jsonObject = new JSONObject(responseJSON);
            int id = (Integer) jsonObject.get(CUSTOMER_ID);
            String name = (String) jsonObject.get(CUSTOMER_NAME);
            int couponId = (Integer) jsonObject.get(COUPON_ID);
            return new Coupon(id, name, couponId);
        } catch (JSONException e) {
            Log.e("Parser", "Exception While Parsing.");
        }
        return null;
    }
}
