package com.example.faloka_mobile.Model;

public class Product {

    String brand;
    String name;
    String price;
    int image;

    public Product() {
    }

    public Product(String brand, String name, String price, int image) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
