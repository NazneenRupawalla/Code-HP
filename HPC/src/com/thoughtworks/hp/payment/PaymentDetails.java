package com.thoughtworks.hp.payment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.thoughtworks.hp.R;

public class PaymentDetails extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment);
		TextView paymentAmount = (TextView) this.findViewById(R.id.amount);

		SharedPreferences settings = getSharedPreferences(
				ServiceConstants.USER_SHOPPING_CART_DETAILS, 0);
		String cost = settings.getString(ServiceConstants.TOTAL_AMOUNT, "0");
		paymentAmount.setText(cost);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode != 0) {
			setResult(1);
			this.finish();
		}
	}

}
