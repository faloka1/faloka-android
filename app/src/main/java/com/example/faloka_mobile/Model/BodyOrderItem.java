package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BodyOrderItem implements Parcelable {
    private int quantity;
    @SerializedName("variant_id")
    private int variantID;
    @SerializedName("product_id")
    private int productID;

    public BodyOrderItem(){

    }

    protected BodyOrderItem(Parcel in) {
        setQuantity(in.readInt());
        setVariantID(in.readInt());
        setProductID(in.readInt());
    }

    public static final Creator<BodyOrderItem> CREATOR = new Creator<BodyOrderItem>() {
        @Override
        public BodyOrderItem createFromParcel(Parcel in) {
            return new BodyOrderItem(in);
        }

        @Override
        public BodyOrderItem[] newArray(int size) {
            return new BodyOrderItem[size];
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
