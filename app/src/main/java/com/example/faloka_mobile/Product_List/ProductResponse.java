package com.example.faloka_mobile.Product_List;

public class ProductResponse {
    private String name;
    private int image;
    private String brand;
    private int price;

    public ProductResponse(String name, int image, String brand, int price){
        this.name = name;
        this.image = image;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
