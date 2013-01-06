package com.tw.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.hp.R;
import com.tw.model.Coupon;
import com.tw.parser.CustomerParser;
import com.tw.tasks.PostAsyncTask;

public class DisplayActivity extends Activity {

    public static final String SCAN_RESULT = "scan-result";
    private Coupon coupon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.display);
        if (bundle != null) {
            String jsonContent = (String) bundle.get(SCAN_RESULT);
            coupon = getCoupon(jsonContent);
            displayContents(coupon);
        }
        Button home = (Button) findViewById(R.id.home_btn);
        home.setOnClickListener(homeListener);

        Button approveBtn = (Button) findViewById(R.id.approve_btn);
        approveBtn.setOnClickListener(approveListener);
    }

    public Button.OnClickListener homeListener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent homeIntent = new Intent(v.getContext(), HomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(homeIntent, RESULT_FIRST_USER);
        }
    };

    public Button.OnClickListener approveListener = new Button.OnClickListener() {
        public void onClick(View v) {
            new PostAsyncTask(coupon).execute();
            Toast.makeText(v.getContext(), "Posted to Server", 15);
        }
    };

    private void displayContents(Coupon coupon) {
        if (coupon != null) {
            setDisplayElements(coupon);
        } else {
            setError();
        }
    }

    private Coupon getCoupon(String content) {
        return CustomerParser.parseResponse(content);
    }

    private void setError() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.result_layout);
        linearLayout.setVisibility(View.GONE);
        setText(R.id.error, "Invalid QR Code");
    }

    private void setDisplayElements(Coupon coupon) {
        setText(R.id.coupon_id, String.valueOf(coupon.getId()));
        setText(R.id.cust_id, String.valueOf(coupon.getCustomerId()));
        setText(R.id.cust_name, coupon.getCustomerName());
    }

    private void setText(int viewElement, String id) {
        TextView custId = (TextView) findViewById(viewElement);
        custId.setText(id);
    }
}
