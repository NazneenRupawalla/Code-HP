package com.thoughtworks.hp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.models.Product;

import java.util.List;

public class ProductListAdapter extends AbstractItemListingAdapter<Product> {

    static class ProductViewHolder extends ViewHolder {
        TextView productName;
        TextView productUOM;
        TextView productPrice;
    }

    public ProductListAdapter(Context context, int layout, List<Product> products) {
        super(context, layout, products);
    }

    @Override
    protected void populateHolderElements(AbstractItemListingAdapter.ViewHolder holder, View convertView) {
        ((ProductViewHolder)holder).productName = (TextView) convertView.findViewById(R.id.product_name);
        ((ProductViewHolder)holder).productPrice = (TextView) convertView.findViewById(R.id.product_price);
        ((ProductViewHolder)holder).productUOM = (TextView) convertView.findViewById(R.id.product_uom);
    }

    @Override
    protected AbstractItemListingAdapter.ViewHolder getViewHolder() {
        return new ProductViewHolder();
    }

    @Override
    protected void populateDetailsForLineItem(ViewHolder holder, Product product, View convertView) {
        TextView productNameTextView = ((ProductViewHolder)holder).productName;
        productNameTextView.setText(product.getName());

        TextView productCostTextView = ((ProductViewHolder)holder).productPrice;
        productCostTextView.setText(product.getCostAsString());

        TextView productUOMTextView = ((ProductViewHolder)holder).productUOM;
        productUOMTextView.setText(product.getUom());
    }

}
