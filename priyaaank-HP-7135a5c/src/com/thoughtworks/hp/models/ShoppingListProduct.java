package com.thoughtworks.hp.models;

import com.thoughtworks.hp.datastore.ShoppingListProductTable;

public class ShoppingListProduct {

    private static int FULLFILLED_FLAG = 1;
    private static int PENDING_FLAG = 0;

    private long shoppingListId;
    private long productId;
    private int quantity;
    private int fulfilled;

    private Product product;

    private static ShoppingListProductTable shoppingListProductTable;

    static {
        shoppingListProductTable = new ShoppingListProductTable();
    }

    public ShoppingListProduct(long productId, long shoppingListId) {
        this.productId = productId;
        this.shoppingListId = shoppingListId;
        this.quantity = 1;
    }

    public ShoppingListProduct(long productId, long shoppingListId, int quantity, int fulfilled) {
        this(productId, shoppingListId);
        this.quantity = quantity;
        this.fulfilled = fulfilled;
    }

    public ShoppingListProduct(Product product, long shoppingListId) {
        this(product.getId(), shoppingListId);
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getShoppingListId() {
        return shoppingListId;
    }

    public long getProductId() {
        return productId;
    }

    public double getUnitCost() {
        if(product == null) product = product();
        return product.getPrice();
    }

    public Product product() {
        if(product != null) return product;
        return Product.findById(productId);
    }

    public void toggleFulfilled() {
        this.fulfilled = this.fulfilled == PENDING_FLAG ? FULLFILLED_FLAG : PENDING_FLAG;
    }

    public double totalCost() {
        if(product == null) this.product = product();
        return quantity * product.getPrice();
    }

    public static ShoppingListProduct create(ShoppingListProduct newShoppingListProduct) {
        return shoppingListProductTable.create(newShoppingListProduct);
    }

    public String getName() {
        if(product == null) this.product = product();
        return product.getName();
    }

    public void save() {
        shoppingListProductTable.update(this);
    }

    public int getFulfilled() {
        return fulfilled;
    }

    public boolean isFulfilled() {
        return fulfilled == FULLFILLED_FLAG;
    }
}
