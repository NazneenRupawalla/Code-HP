package com.tw.backkick.viewmodels;

public class CouponLink {
    private String displayText;
    private String link;

    public CouponLink(String displayText, String link) {
        this.displayText = displayText;
        this.link = link;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getLink() {
        return link;
    }
}
