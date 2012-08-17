package com.thoughtworks.hp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.models.ShoppingList;

import java.util.List;

public class ShoppingListListingAdapter extends AbstractItemListingAdapter<ShoppingList> {

    static class ShoppingListViewHolder extends ViewHolder {
        TextView shoppingListName;
    }

    public ShoppingListListingAdapter(Context context, int layout, List<ShoppingList> shoppingLists) {
        super(context, layout, shoppingLists);
    }

    @Override
    protected void populateHolderElements(AbstractItemListingAdapter.ViewHolder holder, View convertView) {
        ((ShoppingListViewHolder)holder).shoppingListName = (TextView) convertView.findViewById(R.id.shopping_list_name_text);
    }

    @Override
    protected AbstractItemListingAdapter.ViewHolder getViewHolder() {
        return new ShoppingListViewHolder();
    }

    @Override
    protected void populateDetailsForLineItem(ViewHolder holder, ShoppingList shoppingList, View convertView) {
        TextView shoppingListName = ((ShoppingListViewHolder)holder).shoppingListName;
        shoppingListName.setText(shoppingList.getName());
    }
}
