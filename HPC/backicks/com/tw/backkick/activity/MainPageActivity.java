package com.tw.backkick.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.thoughtworks.hp.R;
import com.tw.backkick.adapters.FeatureListAdapter;
import com.tw.backkick.viewmodels.Feature;

public class MainPageActivity extends ListActivity {
    private FeatureListAdapter featureListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        featureListAdapter = new FeatureListAdapter(this, R.layout.row_feature_info, featureNameList());
        this.setListAdapter(featureListAdapter);
    }

    private List<Feature> featureNameList() {
        List<Feature> featureNameList = new ArrayList<Feature>();
        featureNameList.add(new Feature("View Coupons", R.drawable.coupon));
        featureNameList.add(new Feature("Download Coupons", R.drawable.download));
        featureNameList.add(new Feature("Loyalty points", R.drawable.points));
        return featureNameList;
    }

    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
        Intent intent=null;
        switch (position){
            case 0:
                intent = new Intent(view.getContext(), PagerActivity.class);
                break;
            case 1:
                intent = new Intent(view.getContext(), NewCoupons.class);
                break;
            case 2:
                intent = new Intent(view.getContext(), DisplayLoyaltyPoints.class);
                break;
        }
        startActivity(intent);
    }
}
