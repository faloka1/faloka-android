package com.example.faloka_mobile.Model;

public class InspireMeProductVariant {

    Product product;
    Variant variant;

    public InspireMeProductVariant(Product product, Variant variant){
        this.product = product;
        this.variant = variant;
    }

    public Product  getProduct() {
        return product;
    }

    public void setProduct(Product  product) {
        this.product = product;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }



}
