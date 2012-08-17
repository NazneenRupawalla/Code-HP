package com.example.imagecapture;

import java.io.File;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class ProductSearchActivity extends Activity {

	private com.iqengines.javaiqe.javaiqe iqe;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_search);
		Intent intent=getIntent();
        final String fileName=intent.getStringExtra("fileName");
		progressDialog = ProgressDialog.show(ProductSearchActivity.this, "", "Processing.Please Wait...");

		new Thread() {

			private String objData;

			public void run() {

				try{

					final String KEY = "40abd50a367348c89262d5fe4efd9724";
					final String SECRET = "3fa4ab025107460bb3db9ebd14ea1433";

					/*
					 * An API object is initialized using the API key and secret
					 */
					iqe = new com.iqengines.javaiqe.javaiqe(KEY, SECRET);

					/*
					 * You can quickly query an image and retrieve results by doing:
					 */

					File testFile = new File(fileName);

					// Query
					com.iqengines.javaiqe.javaiqe.IQEQuery query = iqe.query(testFile);
					// Update
					String update=iqe.update();
					// Result
					String result = iqe.result(query.getQID(), true);
					JSONObject ResultDataJSON = new JSONObject(result);


					JSONObject resultJSON=ResultDataJSON.getJSONObject("data");
					if (resultJSON.has("results"))
					{
						JSONObject objJSON = new JSONObject(resultJSON.getString("results"));
						objData = objJSON.getString("labels");

					}


				} catch (Exception e) {

					Log.e("tag", e.getMessage());

				}

				// dismiss the progress dialog

				progressDialog.dismiss();
				Intent suggestProduct=new Intent(ProductSearchActivity.this,DisplaySuggestion.class);
				suggestProduct.putExtra("productName", (CharSequence)objData);
				startActivity(suggestProduct);

			}

		}.start();

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_product_search, menu);
		return true;
	}
}
