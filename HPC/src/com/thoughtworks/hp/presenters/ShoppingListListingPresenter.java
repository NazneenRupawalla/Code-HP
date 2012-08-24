package com.thoughtworks.hp.presenters;

import com.thoughtworks.hp.datastore.ShoppingListTable;

public class ShoppingListListingPresenter {

    public String totalCountLable() {
        int shoppingListSize = shoppingListCount();
        return plural(shoppingListSize) ? shoppingListSize + " Lists" : shoppingListSize + " List";
    }

    private boolean plural(int size) {
        return size > 1;
    }

    private int shoppingListCount() {
        return new ShoppingListTable().findAll().size();
    }

}
