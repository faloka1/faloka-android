package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResponse implements Parcelable {
    @SerializedName("category")
    private List<Category> categoryList;
    @SerializedName("product")
    private List<Product> productList;
    private int count;

    protected ProductListResponse(Parcel in) {
        setCategoryList(in.createTypedArrayList(Category.CREATOR));
        setProductList(in.createTypedArrayList(Product.CREATOR));
        setCount(in.readInt());
    }

    public static final Creator<ProductListResponse> CREATOR = new Creator<ProductListResponse>() {
        @Override
        public ProductListResponse createFromParcel(Parcel in) {
            return new ProductListResponse(in);
        }

        @Override
        public ProductListResponse[] newArray(int size) {
            return new ProductListResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(getCategoryList());
        parcel.writeTypedList(getProductList());
        parcel.writeInt(getCount());
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
