package com.example.imagecapture;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class DisplaySuggestion extends Activity {

    private TextView productText;
	private TextView alternateText;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_suggestion);
        Intent intent=getIntent();
        String productName=intent.getStringExtra("productName");
        if(productName.equals("No Match"))
        {
        	alternateText=(TextView)findViewById(R.id.textView2);
        	alternateText.setText("Sorry");
            
        }
        productText=(TextView)findViewById(R.id.productNameText);
        productText.setText(productName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_suggestion, menu);
        return true;
    }
}
