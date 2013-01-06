package com.tw.backkick.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.thoughtworks.hp.R;
import com.tw.backkick.adapters.CouponListAdapter;
import com.tw.backkick.service.MyDownloader;
import com.tw.backkick.viewmodels.CouponLink;

public class NewCoupons extends ListActivity {
    private List<CouponLink> couponLinks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initCoupons();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_coupons);
        TextView couponCounts = (TextView) findViewById(R.id.coupons_count);
        couponCounts.setText("2 Coupons Available");
        setListAdapter(new CouponListAdapter(this, R.layout.row_coupon, couponLinks));
        ListView listView = getListView();
        listView.setOnItemClickListener(listClickListener());
        setBackButtonListener();
    }

    private AdapterView.OnItemClickListener listClickListener() {
        return new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MyDownloader.class);
                TextView link = (TextView) view.findViewById(R.id.coupon_link);
                intent.putExtra("url", link.getText());
                startService(intent);
            }
        };
    }

    private void setBackButtonListener() {
        ImageView backImage = (ImageView) findViewById(R.id.back_to_home_button);
        backImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent shoppingListActivity = new Intent(NewCoupons.this, MainPageActivity.class);
                startActivity(shoppingListActivity);
            }
        });

    }

    private void initCoupons() {
        couponLinks = new ArrayList<CouponLink>();
        couponLinks.add(new CouponLink("Coupon1", "http://backkick-service.herokuapp.com/images/coupon1.png"));
        couponLinks.add(new CouponLink("Coupon2", "http://backkick-service.herokuapp.com/images/coupon2.png"));
    }

}
