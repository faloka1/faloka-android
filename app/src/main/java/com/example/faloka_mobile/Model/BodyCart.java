package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BodyCart implements Parcelable {
    private int quantity;
    @SerializedName("product_id")
    private int productID;
    @SerializedName("variant_id")
    private int variantID;

    public BodyCart(){

    }

    protected BodyCart(Parcel in) {
        setQuantity(in.readInt());
        setProductID(in.readInt());
        setVariantID(in.readInt());
    }

    public static final Creator<BodyCart> CREATOR = new Creator<BodyCart>() {
        @Override
        public BodyCart createFromParcel(Parcel in) {
            return new BodyCart(in);
        }

        @Override
        public BodyCart[] newArray(int size) {
            return new BodyCart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getQuantity());
        parcel.writeInt(getProductID());
        parcel.writeInt(getVariantID());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }
}
