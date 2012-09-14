package com.thoughtworks.hp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.thoughtworks.hp.activities.ShoppingListListingActivity;
import com.thoughtworks.hp.epromos.SelectDealActivity;

public class HomePage extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_page);

        bindShoppingDealsButton();
        bindShoppingListButton();
        
	}
	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		Intent intent =new Intent(Intent.ACTION_MAIN);
//		intent.addCategory(Intent.CATEGORY_HOME);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
//	}

    private void bindShoppingDealsButton() {
        View dealsButton = this.findViewById(R.id.deals_image_button);
        dealsButton.setOnClickListener(new View.OnClickListener() {
          
        	@Override
        	public void onClick(View view) {
                Intent promosActivity = new Intent(HomePage.this, SelectDealActivity.class);
                startActivity(promosActivity);
            }
        });
    }


    private void bindShoppingListButton() {
        View shoppingListButton = this.findViewById(R.id.shopping_list_image_button);
        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            
        	@Override
            public void onClick(View view) {
                Intent shoppingListActivity = new Intent(HomePage.this, ShoppingListListingActivity.class);
                startActivityForResult(shoppingListActivity,3);
            }
        });
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode==1)
		{
			Toast.makeText(HomePage.this, "Posted DiscountCoupon ", Toast.LENGTH_SHORT).show();
			
		}

    }
    
}    
