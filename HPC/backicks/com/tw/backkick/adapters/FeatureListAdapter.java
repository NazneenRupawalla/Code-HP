package com.tw.backkick.adapters;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtworks.hp.R;
import com.tw.backkick.viewmodels.Feature;

public class FeatureListAdapter extends ArrayAdapter<Feature> {

    private Context context;
    private int rowFeature;
    private List<Feature> features;

    public FeatureListAdapter(Context context, int rowFeature, List<Feature> features) {
        super(context, rowFeature, features);
        this.context = context;
        this.rowFeature = rowFeature;
        this.features = features;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(rowFeature, null);
        }
        Feature feature = features.get(position);
        if (feature != null) {
            ImageView iconContainer = (ImageView) view.findViewById(R.id.feature_icon);
            TextView featureName = (TextView) view.findViewById(R.id.feature_text);
            iconContainer.setImageResource(feature.getImageId());
            featureName.setText(feature.getFeature());
        }
        return view;
    }
}
