package com.thoughtworks.hp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractItemListingAdapter<T> extends ArrayAdapter<T> {

    private Context context;
    private int layout;
    private List<T> itemList = new ArrayList<T>();

    static class ViewHolder { }

    public AbstractItemListingAdapter(Context context, int layout, List<T> itemList) {
        super(context, layout);
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = setTagWithViewHolder(convertView);
        }

        holder = (ViewHolder) convertView.getTag();

        T item = itemList.get(position);
        if(item != null) populateDetailsForLineItem(holder, item, convertView);

        return convertView;
    }

    private View setTagWithViewHolder(View convertView) {
        ViewHolder holder = getViewHolder();
        LayoutInflater view = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = view.inflate(layout, null);

        populateHolderElements(holder, convertView);

        convertView.setTag(holder);
        return convertView;
    }

    protected abstract void populateHolderElements(ViewHolder holder, View convertView);

    protected abstract ViewHolder getViewHolder();

    protected abstract  void populateDetailsForLineItem(ViewHolder holder, T item, View convertView);

    @Override
    public int getCount() {
        return itemList.size();
    }
}
