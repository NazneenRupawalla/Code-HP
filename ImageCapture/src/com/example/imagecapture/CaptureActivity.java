package com.example.imagecapture;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CaptureActivity extends Activity {

	private static final int REQUEST_IMAGE=100;
	private Button captureButton;
	private ImageView imageView;
	private Bitmap userImage;
	private String selectedImagePath;
	private ProgressDialog progressDialog;
	private String filename;
	private String photoFile;
	private String filePath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		captureButton=(Button)findViewById(R.id.capture);
		captureButton.setOnClickListener(listener);

		imageView=(ImageView)findViewById(R.id.image);


	}

	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		if(requestCode==REQUEST_IMAGE && resultCode==Activity.RESULT_OK){
			filePath=Environment.getExternalStorageDirectory()+"/"+photoFile;
			decodeFile(filePath);
			try {
				executeMultipartPost();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}


	private void executeMultipartPost() throws IOException {
		 
		    File pictureFile = new File(filePath);
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
			userImage.compress(CompressFormat.JPEG, 100, bos);

		    try {
		      FileOutputStream fos = new FileOutputStream(pictureFile);
		      fos.write(bos.toByteArray());
		      fos.close();
		      Toast.makeText(CaptureActivity.this, "New Image saved:" + photoFile,
		          Toast.LENGTH_LONG).show();
		    } catch (Exception error) {
		      Toast.makeText(CaptureActivity.this, "Image could not be saved.",
		          Toast.LENGTH_LONG).show();
		    }
		Intent productSearch=new Intent(CaptureActivity.this,ProductSearchActivity.class);
		productSearch.putExtra("fileName", filePath);
		startActivity(productSearch);

		
		
	}
	
	 private File getDir() {
		    File sdDir = Environment
		      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		    return new File(sdDir, "CameraAPIDemo");
		  }

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}




	public void decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE_HEIGHT = 600;
		final int REQUIRED_SIZE_WIDTH = 400;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		scale=Math.min(height_tmp/REQUIRED_SIZE_HEIGHT,width_tmp/REQUIRED_SIZE_WIDTH);
		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		userImage = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(filePath, o2), 400, 600, false);
		imageView.setImageBitmap(userImage);

	}

		



	private View.OnClickListener listener=new View.OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
		    String date = dateFormat.format(new Date());
		    photoFile = "Picture_" + date + ".jpg";
		    File photo=new File(Environment.getExternalStorageDirectory(),photoFile);
		    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		    //intent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
		    startActivityForResult(intent, REQUEST_IMAGE);
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
