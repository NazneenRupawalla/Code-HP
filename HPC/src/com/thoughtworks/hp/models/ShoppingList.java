package com.thoughtworks.hp.models;

import com.thoughtworks.hp.datastore.ShoppingListProductTable;
import com.thoughtworks.hp.datastore.ShoppingListTable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ShoppingList {

    public static final String SHOPPING_LIST_ID = "SHOPPING_LIST_ID";

    private long id;
    private String name;
    private List<ShoppingListProduct> shoppingListProducts;

    private static ShoppingListProductTable shoppingListProductTable;
    private static ShoppingListTable shoppingListTable;

    static {
        shoppingListTable = new ShoppingListTable();
        shoppingListProductTable = new ShoppingListProductTable();
    }

    public ShoppingList(long id, String name) {
        this.id = id;
        this.name = ((name == null || name.trim().length() == 0) ? "New List" : name);
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<ShoppingListProduct> shoppingListProducts() {
        return Collections.unmodifiableList(shoppingListProductTable.findShoppingListProductByShoppingListId(id));
    }

    public int totalDistinctProducts() {
        loadShoppingListProductsIfNotLoaded();
        return shoppingListProducts.size();
    }

    public int totalProductUnitCount() {
        loadShoppingListProductsIfNotLoaded();
        int totalUnitCount = 0;
        for(ShoppingListProduct product : shoppingListProducts) {
            totalUnitCount += product.getQuantity();
        }
        return totalUnitCount;
    }

    public double totalCost() {
        loadShoppingListProductsIfNotLoaded();
        double totalCost = 0;
        for(ShoppingListProduct product : shoppingListProducts) {
            totalCost += product.totalCost();
        }
        return new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    private void loadShoppingListProductsIfNotLoaded() {
        if(shoppingListProducts == null) reloadSelf();
    }

    public static ShoppingList findById(long shoppingListId) {
        return shoppingListTable.findById(shoppingListId);
    }

    public void addNewProduct(Product product) {
        ShoppingListProduct.create(new ShoppingListProduct(product, id));
        reloadSelf();
    }

    private void reloadSelf() {
        shoppingListProducts = shoppingListProducts();
    }

    public void toggleCompletenessOfProductAt(long position) {
        ShoppingListProduct shoppingListProduct = shoppingListProducts.get((int) position);
        shoppingListProduct.toggleFulfilled();
        shoppingListProduct.save();
        reloadSelf();
    }
}
