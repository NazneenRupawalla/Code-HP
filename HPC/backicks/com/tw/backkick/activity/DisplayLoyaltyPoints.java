package com.tw.backkick.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.thoughtworks.hp.R;
import com.tw.backkick.tasks.FetchLoyaltyPoints;

public class DisplayLoyaltyPoints extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyalty_points);
        new FetchLoyaltyPoints(this).execute();
        setBackButtonListener();
    }

    private void setBackButtonListener() {
            ImageView backImage = (ImageView) findViewById(R.id.back_to_home_button);
            backImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent shoppingListActivity = new Intent(DisplayLoyaltyPoints.this, MainPageActivity.class);
                    startActivity(shoppingListActivity);
                }
            });
        }


}
