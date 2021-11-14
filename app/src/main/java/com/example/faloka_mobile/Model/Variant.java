package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Variant implements Parcelable {
    private int id;
    private String name;
    @SerializedName("product_id")
    private int productID;
    @SerializedName("variants_image")
    private List<VariantImage> variantImageList;

    public Variant(){}
    public Variant(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setProductID(in.readInt());
        setVariantImageList(in.createTypedArrayList(VariantImage.CREATOR));
    }

    public static final Creator<Variant> CREATOR = new Creator<Variant>() {
        @Override
        public Variant createFromParcel(Parcel in) {
            return new Variant(in);
        }

        @Override
        public Variant[] newArray(int size) {
            return new Variant[size];
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
        parcel.writeInt(getProductID());
        parcel.writeTypedList(getVariantImageList());
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

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public List<VariantImage> getVariantImageList() {
        return variantImageList;
    }

    public void setVariantImageList(List<VariantImage> variantImageList) {
        this.variantImageList = variantImageList;
    }
}
