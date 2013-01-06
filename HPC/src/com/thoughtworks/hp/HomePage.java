package com.thoughtworks.hp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thoughtworks.hp.activities.ShoppingListListingActivity;
import com.thoughtworks.hp.epromos.SelectDealActivity;
import com.tw.activities.HomeActivity;
import com.tw.backkick.activity.MainPageActivity;

public class HomePage extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_page);

		bindShoppingDealsButton();
		bindShoppingListButton();
		bindBackKicksButton();
		bindBackKicksScanner();

	}

	private void bindShoppingDealsButton() {
		View dealsButton = this.findViewById(R.id.deals_image_button);
		dealsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent promosActivity = new Intent(HomePage.this,
						SelectDealActivity.class);
				startActivity(promosActivity);
			}
		});
	}
	
	private void bindBackKicksButton() {
		View dealsButton = this.findViewById(R.id.backkicks_share_button);
		dealsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent backkicksShare = new Intent(HomePage.this,
						MainPageActivity.class);
				startActivity(backkicksShare);
			}
		});
	}
	
	private void bindBackKicksScanner() {
		View dealsButton = this.findViewById(R.id.backkicks_scanner_button);
		dealsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent backkicksShare = new Intent(HomePage.this,
						HomeActivity.class);
				startActivity(backkicksShare);
			}
		});
	}

	private void bindShoppingListButton() {
		View shoppingListButton = this
				.findViewById(R.id.shopping_list_image_button);
		shoppingListButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent shoppingListActivity = new Intent(HomePage.this,
						ShoppingListListingActivity.class);
				startActivityForResult(shoppingListActivity, 3);
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		/*
		 * if(requestCode==1) { Toast.makeText(HomePage.this,
		 * "Posted DiscountCoupon ", Toast.LENGTH_SHORT).show();
		 * 
		 * }
		 */
	}
}
