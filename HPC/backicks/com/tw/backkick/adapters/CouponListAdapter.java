package com.tw.backkick.adapters;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thoughtworks.hp.R;
import com.tw.backkick.viewmodels.CouponLink;

public class CouponListAdapter extends ArrayAdapter<CouponLink> {

    private Context context;
    private int rowCoupon;
    private List<CouponLink> couponLink;

    public CouponListAdapter(Context context, int rowCoupon, List<CouponLink> couponLink) {
        super(context, rowCoupon, couponLink);
        this.context = context;
        this.rowCoupon = rowCoupon;
        this.couponLink = couponLink;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(rowCoupon, null);
        }
        CouponLink couponLinks = couponLink.get(position);
        if (couponLinks != null) {
            TextView link = (TextView) view.findViewById(R.id.coupon_link);
            TextView displayText = (TextView) view.findViewById(R.id.coupon_display_text);
            displayText.setText(couponLinks.getDisplayText());
            link.setText(couponLinks.getLink());
        }
        return view;
    }
}
