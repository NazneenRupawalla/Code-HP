package com.tw.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thoughtworks.hp.R;
import com.tw.integration.IntentIntegrator;
import com.tw.integration.IntentResult;


public class HomeActivity extends Activity {

    public static final String SCAN_RESULT = "scan-result";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button scanButton = (Button) findViewById(R.id.scan_btn);
        scanButton.setOnClickListener(scan);
    }

    public Button.OnClickListener scan = new Button.OnClickListener() {
        public void onClick(View v) {
            IntentIntegrator integrator = new IntentIntegrator(HomeActivity.this);
            integrator.initiateScan();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_CANCELED) {
            finish();
            return;
        }
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Intent displayIntent = new Intent(this, DisplayActivity.class);
        Bundle bundle = new Bundle();
        if (scanResult != null) {
            bundle.putString(SCAN_RESULT, scanResult.getContents());
        } else {
            bundle.putString(SCAN_RESULT, "Error");
        }
        displayIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        displayIntent.putExtras(bundle);
        startActivityForResult(displayIntent, RESULT_FIRST_USER);
    }
}
