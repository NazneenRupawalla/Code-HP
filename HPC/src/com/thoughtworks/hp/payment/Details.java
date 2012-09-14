package com.thoughtworks.hp.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.thoughtworks.hp.R;

public class Details extends Activity implements OnClickListener {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pickup_time);
	        Button submitButton=(Button)findViewById(R.id.submit);
	        submitButton.setOnClickListener(this);
	        Button cancelButton=(Button)findViewById(R.id.cancel);
	        cancelButton.setOnClickListener(this);
	        
	    }

		@Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if(requestCode==1 && resultCode!=0){
	        	setResult(1);
	        	this.finish();
	        }
	    }


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.submit:
				Intent paymentIntent=new Intent(Details.this,PaymentDetails.class);
				startActivityForResult(paymentIntent,1);
				break;
			case R.id.cancel:
				setResult(RESULT_CANCELED);
				this.finish();
				break;
				
			}
		}
}
