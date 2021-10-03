package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product implements Parcelable {
    private int id;
    private String name;
    @SerializedName("variants")
    private List<Variant> variantList;
    @SerializedName("brands")
    private Brand brand;
    private int price;
    private Pivot pivot;
    private String description;
    private float discount;
    private String slug;

    protected Product(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setVariantList(in.createTypedArrayList(Variant.CREATOR));
        setBrand(in.readParcelable(Brand.class.getClassLoader()));
        setPrice(in.readInt());
        setPivot(in.readParcelable(Pivot.class.getClassLoader()));
        setDescription(in.readString());
        setDiscount(in.readFloat());
        setSlug(in.readString());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
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
        parcel.writeTypedList(getVariantList());
        parcel.writeParcelable(getBrand(), i);
        parcel.writeInt(getPrice());
        parcel.writeParcelable(getPivot(), i);
        parcel.writeString(getDescription());
        parcel.writeFloat(getDiscount());
        parcel.writeString(getSlug());
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

    public List<Variant> getVariantList() {
        return variantList;
    }

    public String getProductImageURL(){
        Variant variant= variantList.get(0);
        List<VariantImage> variantImageList = variant.getVariantImageList();
        VariantImage variantImage = variantImageList.get(0);
        return variantImage.getImageURL();
    }

    public void setVariantList(List<Variant> variantList) {
        this.variantList = variantList;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}