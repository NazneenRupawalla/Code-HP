package com.tw.model;

public class Coupon {
    private int id;
    private int customerId;
    private String customerName;

    public Coupon(int id, String customerName, int customerId) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getCustomerId() {
        return customerId;
    }
}
