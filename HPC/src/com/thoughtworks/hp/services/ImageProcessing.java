package com.thoughtworks.hp.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.IntentService;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class ImageProcessing extends IntentService {

	public ImageProcessing() {
		super("myIntentService");
		// TODO Auto-generated constructor stub
	}

	private Bitmap userImage;
	private Messenger messenger;
	private String barcodeId;
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private void compressImage(String filePath) {
		// TODO Auto-generated method stub
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);
		final int REQUIRED_SIZE_HEIGHT = 600;
		final int REQUIRED_SIZE_WIDTH = 400;
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		scale=Math.min(height_tmp/REQUIRED_SIZE_HEIGHT,width_tmp/REQUIRED_SIZE_WIDTH);
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		userImage = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(filePath, o2), 400, 600, false);
		
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		messenger=(Messenger) intent.getExtras().get("handler");
		String filename = intent.getExtras().getString("filename");
		compressImage(filename);
		Callable imageProcessor = new ImageProcessor(filename,userImage);
		 ExecutorService pool = Executors.newFixedThreadPool(3);
		      Future future = pool.submit(imageProcessor);
		      try {
		    	  barcodeId=(String) future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		    Message msg = Message.obtain();
		    Bundle data= new Bundle();
		    data.putString("barcodeID",barcodeId);
		    msg.setData(data);
		    try {
				messenger.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
