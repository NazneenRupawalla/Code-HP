package com.tw.backkick.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.thoughtworks.hp.R;

public class MyDownloader extends IntentService {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private int totalFileSize;

    public MyDownloader() {
        super("MyDownloader");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String link = (String) intent.getExtras().get("url");
        int totalBytesRead =0;
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            totalFileSize = connection.getContentLength()/1024;
            String fileName = getFileName(link);
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file),1024);
            byte[] data = new byte[1024];
            int bytesRead;
            startProgressBar(fileName);
            while((bytesRead = bis.read(data,0,data.length)) >= 0){
                totalBytesRead += bytesRead;
                bos.write(data, 0, bytesRead);
                updateProgressBar(totalBytesRead);
            }
            endProgressBar(fileName);
            bos.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void endProgressBar(String fileName) {
        builder.setContentText("Finished downloading "+ fileName).setProgress(0,0,false);
        notificationManager.notify(0,builder.build());
    }

    private void updateProgressBar(int totalBytesRead) {
        builder.setProgress(totalFileSize,totalBytesRead/1024,false);
        notificationManager.notify(0,builder.build());
    }

    private void startProgressBar(String fileName) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setContentText("Downloading " + fileName + "...").setSmallIcon(R.drawable.hp);

    }

    private String getFileName(String link) {
        return link.substring(link.lastIndexOf("/")+1);
    }
}
