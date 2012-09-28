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

    private View homeDelivery;
	private RelativeLayout home_delivery_layout;
	private RelativeLayout store_pickup_layout;
	private View inStorePickup;
	private int delivery_mode;
	private View proceedToPaymentHomeDelivery;
	private TextView fullName;
	private TextView deliveryMessage;
	private TextView addressLine;
	private TextView city;
	private TextView number;
	private View proceedToPaymentStorePickUp;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_options);
        inStorePickup=this.findViewById(R.id.store_pickup_button);
        homeDelivery=this.findViewById(R.id.home_delivery_button);
        proceedToPaymentHomeDelivery=this.findViewById(R.id.proceed_to_payment_button);
        proceedToPaymentStorePickUp=this.findViewById(R.id.proceed_to_payment_button);
        
         home_delivery_layout = (RelativeLayout)findViewById(R.id.home_delivery_layout);
        store_pickup_layout = (RelativeLayout)findViewById(R.id.store_pickup_layout);
		deliveryMessage=(TextView)findViewById(R.id.home_delivery_message);
		fullName = (TextView)findViewById(R.id.name);
		addressLine = (TextView)findViewById(R.id.address_line_1);
		city = (TextView)findViewById(R.id.city);
		number = (TextView)findViewById(R.id.cell_number);

        bindHomeDeliveryButton();
        bindStorePickupButton();
        bindHomeDeliveryProceedToPayementButton();
        bindStorePickupProceedToPaymentButton();
        
    }
    private void bindStorePickupProceedToPaymentButton() {
    	proceedToPaymentStorePickUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(DeliveryOptions.this,PaymentDetails.class);
				startActivityForResult(intent,1);
			
			}
    		
    	});
    		
		// TODO Auto-generated method stub
		
	}
	private void bindHomeDeliveryProceedToPayementButton() {
		// TODO Auto-generated method stub
    	proceedToPaymentHomeDelivery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if(delivery_mode==HOME_DELIVERY){
					SharedPreferences settings=getSharedPreferences(ServiceConstants.HOME_DELIVERY_DETAILS, 0);
					SharedPreferences.Editor editor=settings.edit();
					fullName.getText();					
					editor.putString(ServiceConstants.FULL_NAME, fullName.getText().toString());
					editor.putString(ServiceConstants.ADDRESS_LINE, addressLine.getText().toString());
					editor.putString(ServiceConstants.CITY, city.getText().toString());
					editor.putString(ServiceConstants.NUMBER,number.getText().toString());
			//	}
				//if(delivery_mode==STORE_PICKUP){
					//store details for store pickup options
			//	}
				
				Intent intent=new Intent(DeliveryOptions.this,PaymentDetails.class);
				startActivityForResult(intent,1);
				
			}
		});
		
	}
	private void bindHomeDeliveryButton(){
    	homeDelivery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				home_delivery_layout.setVisibility(View.VISIBLE);
				store_pickup_layout.setVisibility(View.GONE);
				//delivery_mode=HOME_DELIVERY;
				
			}
		});
    }
   

    private void bindStorePickupButton(){
    	inStorePickup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				home_delivery_layout.setVisibility(View.GONE);
				store_pickup_layout.setVisibility(View.VISIBLE);
				//delivery_mode=STORE_PICKUP;
			
						
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
