package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CartBrand implements Parcelable {
    private Brand brand;
    private List<Product> productList;

    public CartBrand(){

    }

    protected CartBrand(Parcel in) {
        setBrand(in.readParcelable(Brand.class.getClassLoader()));
        setProductList(in.createTypedArrayList(Product.CREATOR));
    }

    public static final Creator<CartBrand> CREATOR = new Creator<CartBrand>() {
        @Override
        public CartBrand createFromParcel(Parcel in) {
            return new CartBrand(in);
        }

        @Override
        public CartBrand[] newArray(int size) {
            return new CartBrand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(getBrand(), i);
        parcel.writeTypedList(getProductList());
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
