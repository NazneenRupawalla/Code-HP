package com.thoughtworks.hp.activities;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import android.widget.ImageView;

public class ImageCapturer {

	private static final int REQUEST_IMAGE = 100;
	private Activity activity;
	private String photoFile;
	private static int count=0;
	

	public ImageCapturer(Activity activity, ImageView imageCaptureButton) {
		this.activity=activity;
		captureImage(imageCaptureButton);
			}

	private void captureImage(ImageView imageCaptureButton) {
        imageCaptureButton.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View view) {
                takePicture();
            }
        });
	}
	public void takePicture(){
//TODO save the image with the dateformat and send it to OnActivity Result
		//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
		//	    String date = dateFormat.format(new Date());
		
	    photoFile = "Picture_" + count++ + ".jpg";
	    File photo=new File(Environment.getExternalStorageDirectory(),photoFile);
	    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
	    startActivityForResult(intent, REQUEST_IMAGE);

		
	}
	
	protected void startActivityForResult(Intent intent, int code) {
        activity.startActivityForResult(intent, code);
    }
	

	
	

}
