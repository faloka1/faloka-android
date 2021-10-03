package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category implements Parcelable {

    private int id;
    private String name;
    private String slug;
    @SerializedName("carousels")
    private List<Carousel> carouselList;
    @SerializedName("sub_categories")
    private List<SubCategory> subCategoryList;
    @SerializedName("products")
    private List<Product> productList;


    protected Category(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setSlug(in.readString());
        setCarouselList(in.createTypedArrayList(Carousel.CREATOR));
        setSubCategoryList(in.createTypedArrayList(SubCategory.CREATOR));
        setProductList(in.createTypedArrayList(Product.CREATOR));
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getSlug());
        parcel.writeTypedList(getCarouselList());
        parcel.writeTypedList(getSubCategoryList());
        parcel.writeTypedList(getProductList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Carousel> getCarouselList() {
        return carouselList;
    }

    public void setCarouselList(List<Carousel> carouselList) {
        this.carouselList = carouselList;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
