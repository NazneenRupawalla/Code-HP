package com.thoughtworks.hp.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.datastore.ProductAvailabilityService;
import com.thoughtworks.hp.datastore.ShoppingListProductTable;

import java.text.SimpleDateFormat;

public class AddQuantityActivity extends Activity {

	EditText editText;
	private long productId;
    private int quantity;
	@SuppressWarnings("unused")
	private long shoppingListId;
	private ShoppingListProductTable shoppingListProductTable;
	private static SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("dd-MM");
	private static String formatter = "%s (%s)";

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.update_quantity);
		shoppingListProductTable = new ShoppingListProductTable();
		productId = getIntent().getLongExtra("product_id", 1L);
		shoppingListId = getIntent().getLongExtra("Shopping_list_id", 0L);
        quantity = getIntent().getIntExtra("quantity",1);
        editText = (EditText) findViewById(R.id.quantity_value);
        editText.setText(String.valueOf(quantity));
		boolean availableStatus = ProductAvailabilityService.isProductAvailable(productId);
		TextView textToChange = (TextView) findViewById(R.id.availabilityText);
		if (!availableStatus) {
			String nextAvailable = simpleDateFormatter.format(ProductAvailabilityService.getProductRestockDate(productId).getTime());
			nextAvailable = String.format(formatter, "No", nextAvailable);
			textToChange.setText(nextAvailable);
			textToChange.setTextColor(getResources().getColor(R.color.red));
		} else {
			textToChange.setText("Available");
			textToChange.setTextColor(getResources().getColor(R.color.green));
		}
	}

	public void incrementQuantity(View view) {
        int quantity = getQuantityFromEditBox() +1;
        editText.setText(String.valueOf(quantity));
        updateQuantityInDatabase();
	}

	public void decrementQuantity(View view) {
        EditText editText = (EditText) findViewById(R.id.quantity_value);
        int quantity = getQuantityFromEditBox()!=0 ? getQuantityFromEditBox()-1 : 0;
        editText.setText(String.valueOf(quantity));
        updateQuantityInDatabase();
	}

    private int getQuantityFromEditBox() {
        editText = (EditText) findViewById(R.id.quantity_value);
        int quantity = Integer.parseInt(editText.getText().toString());
        if(quantity < 0)
            quantity = 0;
        return quantity;
    }

    private void updateQuantityInDatabase() {
//        new ShoppingListProductTable().updateQuantityForProduct(productId, getQuantityFromEditBox());
    }
}
