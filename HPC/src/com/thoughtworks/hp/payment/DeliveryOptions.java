package com.thoughtworks.hp.payment;

import com.thoughtworks.hp.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DeliveryOptions extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_options);
        Button inStorePickup=(Button)findViewById(R.id.store_pickup);
        inStorePickup.setOnClickListener(this);
        Button homeDelivery=(Button)findViewById(R.id.home_delivery);
        homeDelivery.setOnClickListener(this);
        
    }

   

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.store_pickup:
			Intent pickUpIntent=new Intent(DeliveryOptions.this,PaymentDetails.class);
			startActivityForResult(pickUpIntent,1);
			break;
		case R.id.home_delivery:
			Intent paymentIntent=new Intent(DeliveryOptions.this,Details.class);
			startActivityForResult(paymentIntent,1);
			break;
		}
		
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode!=0){
        	setResult(1);
        	this.finish();
        }
        
    }
}
