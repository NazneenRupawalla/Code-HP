package com.tw.backkick.viewmodels;

public class Feature {
    private String feature;
    private int imageId;

    public Feature(String feature, int imageId) {
        this.feature = feature;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getFeature() {
        return feature;
    }
}
