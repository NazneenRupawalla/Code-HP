package com.thoughtworks.hp.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.iqengines.javaiqe.javaiqe;
import com.thoughtworks.hp.datastore.ProductTable;
import com.thoughtworks.hp.models.Product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ImageProcessor {

	private static final int REQUEST_IMAGE = 100;
	private Activity activity;
	private String photoFile;
	private String filePath;
	private Bitmap userImage;
	private javaiqe iqe;
	private String objData;

	public ImageProcessor(Activity activity, ImageView imageCaptureButton) {
		this.activity=activity;
		captureImage(imageCaptureButton);
			}

	private void captureImage(ImageView imageCaptureButton) {
        imageCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
	}
	public void takePicture(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
	    String date = dateFormat.format(new Date());
	    photoFile = "Picture_" + date + ".jpg";
	    File photo=new File(Environment.getExternalStorageDirectory(),photoFile);
	    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		//intent.putExtra(name, value)
	    startActivityForResult(intent, REQUEST_IMAGE);

		
	}
	
	protected void startActivityForResult(Intent intent, int code) {
        activity.startActivityForResult(intent, code);
    }

	public List<Product> processImage() throws JSONException {
		filePath=Environment.getExternalStorageDirectory()+"/"+photoFile;
		decodeFile(filePath);
		try {
			return findByMatchingName(executeMultipartPost());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private List<Product> findByMatchingName(String productName) {
		return new ProductTable().findByMatchingName(productName);
	}

	private String executeMultipartPost() throws IOException, JSONException {
		// TODO Auto-generated method stub
		 File pictureFile = new File(filePath);
	     ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	userImage.compress(CompressFormat.JPEG, 100, bos);
		      FileOutputStream fos = new FileOutputStream(pictureFile);
		      fos.write(bos.toByteArray());
		      fos.close();
		      final String KEY = "40abd50a367348c89262d5fe4efd9724";
			  final String SECRET = "3fa4ab025107460bb3db9ebd14ea1433";

				/*
				 * An API object is initialized using the API key and secret
				 */
				iqe = new com.iqengines.javaiqe.javaiqe(KEY, SECRET);

				/*
				 * You can quickly query an image and retrieve results by doing:
				 */

				File testFile = new File(filePath);

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
					return objData = objJSON.getString("labels");

				}

		      return null;
		
	}

	private void decodeFile(String filePath) {
		// TODO Auto-generated method stub
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
		
	}
	

}
