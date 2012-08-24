package com.thoughtworks.hp.presenters;

import com.thoughtworks.hp.models.Product;
import com.thoughtworks.hp.models.ShoppingList;
import com.thoughtworks.hp.models.ShoppingListProduct;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListPresenter {

    private List<ShoppingListProduct> shoppingListProducts = new ArrayList<ShoppingListProduct>();

    private ShoppingList shoppingList;

    public ShoppingListPresenter(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public List<ShoppingListProduct> shoppingListProducts() {
        return shoppingList.shoppingListProducts();
    }

    public String shoppingListName() {
        return shoppingList.getName();
    }

    public int totalDistinctProductsCount() {
        return shoppingList.totalDistinctProducts();
    }

    public String totalDistinctProductsLabel() {
        return generateItemLabelForCount(totalDistinctProductsCount());
    }

    private String generateItemLabelForCount(int itemCount) {
        StringBuffer label = new StringBuffer(Integer.toString(itemCount));
        label.append(multipleItems(itemCount) ? " Items" : " Item");
        return label.toString();
    }

    public int totalProductUnitCount() {
        return shoppingList.totalProductUnitCount();
    }

    public String totalProductsUnitCountLabel() {
        return generateItemLabelForCount(totalProductUnitCount());
    }

    public String totalShoppingListCostLabel() {
        return Product.INDIAN_CURRENCY_SYMBOL + " " + Double.toString(shoppingList.totalCost());
    }

    public void addSelectedProductToShoppingList(Product product) {
        this.shoppingList.addNewProduct(product);
        this.shoppingListProducts = shoppingList.shoppingListProducts();
    }

    private boolean multipleItems(int itemCount) {
        return itemCount > 1;
    }

    public void toggleCompletenessForItemAt(long position) {
        shoppingList.toggleCompletenessOfProductAt(position);
    }
}
