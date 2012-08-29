package com.thoughtworks.hp.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;

import org.json.JSONObject;

import com.iqengines.javaiqe.javaiqe;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class ImageProcessor implements Callable {

	private String filename;
	private Bitmap userImage;
	private javaiqe iqe;

	public ImageProcessor(String filename, Bitmap userImage) {
		this.filename=filename;
		this.userImage=userImage;
	}

	public String call() throws Exception {
		File pictureFile = new File(filename);
	     ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	userImage.compress(CompressFormat.JPEG, 100, bos);
		      FileOutputStream fos = new FileOutputStream(pictureFile);
		      fos.write(bos.toByteArray());
		      fos.close();
		       		  final String KEY = "ffade78f89c247dc913a88836fdcfb35";
					  final String SECRET = "5c77d8697cee42c38acb43f55c33c5cd";
						iqe = new com.iqengines.javaiqe.javaiqe(KEY, SECRET);
						File testFile = new File(filename);
						com.iqengines.javaiqe.javaiqe.IQEQuery query = iqe.query(testFile);
						iqe.update();			
						String result = iqe.result(query.getQID(), true);
						JSONObject ResultDataJSON = new JSONObject(result);
						

						JSONObject resultJSON=ResultDataJSON.getJSONObject("data");
						String barcodeId = null;
						if (resultJSON.has("results"))
						{
							JSONObject productResult = new JSONObject(resultJSON.getString("results"));
							if(!productResult.getString("labels").equals("No Match"))
							{
							barcodeId=productResult.getString("meta");
						}
						    
						}	    	  
						testFile.delete();
						
		      return barcodeId;
		
		
	}

}
