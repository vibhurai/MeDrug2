package com.kaustubh.medrug;

public class meds_intr {
    private int id;

    private String name;
    private int quantity;
    private double price;
    private String image;
    private int category;

    public meds_intr( int id, String name, int quantity, double price, String image,int category) {
//        this.userId = userId;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.category = category;
    }

//    public int getUserId() {
//        return userId;
//    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getCategory() {
        return category;
    }
}
