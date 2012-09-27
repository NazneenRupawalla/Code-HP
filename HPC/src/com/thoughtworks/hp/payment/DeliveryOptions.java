package com.thoughtworks.hp.payment;

import com.thoughtworks.hp.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DeliveryOptions extends Activity {
	private static final int HOME_DELIVERY=1 ;
	private static final int STORE_PICKUP=2 ;

    private Button homeDelivery;
	private RelativeLayout home_delivery_layout;
	private RelativeLayout store_pickup_layout;
	private Button inStorePickup;
	private int delivery_mode;
	private Button proceedToPayemnt;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_options);
        inStorePickup=(Button)this.findViewById(R.id.store_pickup_button);
        homeDelivery=(Button)this.findViewById(R.id.home_delivery_button);
        proceedToPayemnt=(Button)this.findViewById(R.id.proceed_to_payment_button);
        home_delivery_layout = (RelativeLayout)findViewById(R.id.home_delivery_layout);
        store_pickup_layout = (RelativeLayout)findViewById(R.id.store_pickup_layout);
        bindHomeDeliveryButton();
        bindStorePickupButton();
        bindProceedToPayementButton();
        
    }
    private void bindProceedToPayementButton() {
		// TODO Auto-generated method stub
    	proceedToPayemnt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(delivery_mode==HOME_DELIVERY){
					SharedPreferences settings=getSharedPreferences(ServiceConstants.HOME_DELIVERY_DETAILS, 0);
					SharedPreferences.Editor editor=settings.edit();
					TextView fullName = (TextView)findViewById(R.id.name);
					TextView addressLine = (TextView)findViewById(R.id.address_line_1);
					TextView city = (TextView)findViewById(R.id.city);
					TextView number = (TextView)findViewById(R.id.cell_number);
					
					editor.putString(ServiceConstants.FULL_NAME, (String) fullName.getText());
					editor.putString(ServiceConstants.ADDRESS_LINE, (String) addressLine.getText());
					editor.putString(ServiceConstants.CITY, (String) city.getText());
					editor.putString(ServiceConstants.NUMBER,(String) number.getText());
				}
				if(delivery_mode==STORE_PICKUP){
					//store details for store pickup options
				}
				
				Intent intent=new Intent(DeliveryOptions.this,PaymentDetails.class);
				startActivity(intent);
				
			}
		});
		
	}
	private void bindHomeDeliveryButton(){
    	homeDelivery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				home_delivery_layout.setVisibility(View.VISIBLE);
				store_pickup_layout.setVisibility(View.GONE);
				delivery_mode=HOME_DELIVERY;
			}
		});
    }
   

    private void bindStorePickupButton(){
    	inStorePickup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				home_delivery_layout.setVisibility(View.GONE);
				store_pickup_layout.setVisibility(View.VISIBLE);
				delivery_mode=STORE_PICKUP;
						
			}
		});
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode!=0){
        	setResult(1);
        	this.finish();
        }
        
    }
	
}
