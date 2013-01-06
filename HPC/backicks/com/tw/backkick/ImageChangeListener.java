package com.tw.backkick;

import android.support.v4.view.ViewPager;

import java.util.HashMap;
import java.util.Map;

public class ImageChangeListener extends ViewPager.SimpleOnPageChangeListener {

    private Map<Integer, String> fileName = new HashMap<Integer, String>();
    private String currentImage;

    public ImageChangeListener(Map<Integer, String> fileName) {
        this.fileName = fileName;
        this.currentImage = fileName.get(0);
    }

    @Override
    public void onPageSelected(int position) {
        currentImage = fileName.get(position);
    }

    public String getFileName() {
        return currentImage;
    }
}