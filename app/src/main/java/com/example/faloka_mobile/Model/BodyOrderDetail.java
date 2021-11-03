package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BodyOrderDetail implements Parcelable {
    private int quantity;
    @SerializedName("variant_id")
    private int variantID;
    @SerializedName("product_id")
    private int productID;

    public BodyOrderDetail(){

    }

    protected BodyOrderDetail(Parcel in) {
        setQuantity(in.readInt());
        setVariantID(in.readInt());
        setProductID(in.readInt());
    }

    public static final Creator<BodyOrderDetail> CREATOR = new Creator<BodyOrderDetail>() {
        @Override
        public BodyOrderDetail createFromParcel(Parcel in) {
            return new BodyOrderDetail(in);
        }

        @Override
        public BodyOrderDetail[] newArray(int size) {
            return new BodyOrderDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getQuantity());
        parcel.writeInt(getVariantID());
        parcel.writeInt(getProductID());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
