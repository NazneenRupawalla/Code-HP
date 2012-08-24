package com.thoughtworks.hp.models;

import com.thoughtworks.hp.datastore.ProductTable;

import java.util.List;

public class Product {

    private long id;
    private String name;
    private String barcodeId;
    private String category;
    private double price;
    private String uom;
    public static final String INDIAN_CURRENCY_SYMBOL = "Rs. ";

    private static ProductTable productTable;

    static {
        productTable = new ProductTable();
    }

    public Product(long productId, String name, String barcodeId, String category, double price, String uom) {
        this.id = productId;
        this.name = name;
        this.barcodeId = barcodeId;
        this.category = category;
        this.price = price;
        this.uom = uom;
    }

    public String getName() {
        return this.name;
    }

    public String getCostAsString() {
        return INDIAN_CURRENCY_SYMBOL + Double.toString(this.price);
    }

    public double getPrice() {
        return price;
    }

    public String getUom() {
        return this.uom;
    }

    @Override
    public String toString(){
        return name;
    }

    public long getId() {
        return id;
    }

    public static List<Product> findProductsByMatchingName(String name) {
        return productTable.findByMatchingName(name);
    }

    public static List<Product> findProductsByShoppingListId(long id) {
        return productTable.findByShoppingList(id);
    }

    public static Product findById(long productId) {
        return productTable.findById(productId);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(other == null || !(other instanceof Product)) return false;

        Product otherProduct = (Product) other;
        if(this.id == otherProduct.id) return true;
        if(this.name.equals(otherProduct.name) &&
           this.category.equals(otherProduct.category) &&
           this.barcodeId.equals(otherProduct.barcodeId) &&
           this.uom.equals(otherProduct.uom)) return true;

        return false;
    }
}


