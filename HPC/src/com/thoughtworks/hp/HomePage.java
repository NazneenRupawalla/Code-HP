package com.thoughtworks.hp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
                startActivity(shoppingListActivity);
            }
        });
    }
}
