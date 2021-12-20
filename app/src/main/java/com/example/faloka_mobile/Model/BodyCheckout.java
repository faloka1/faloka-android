package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyCheckout implements Parcelable {
    @SerializedName("payment_id")
    private int paymentID;
    @SerializedName("address_id")
    private int addressID;
    @SerializedName("order_brands")
    private List<BodyOrderBrand> bodyOrderBrandList;

    public BodyCheckout(){

    }
    protected BodyCheckout(Parcel in) {
        setPaymentID(in.readInt());
        setAddressID(in.readInt());
        setBodyOrderBrandList(in.createTypedArrayList(BodyOrderBrand.CREATOR));
    }

    public static final Creator<BodyCheckout> CREATOR = new Creator<BodyCheckout>() {
        @Override
        public BodyCheckout createFromParcel(Parcel in) {
            return new BodyCheckout(in);
        }

        @Override
        public BodyCheckout[] newArray(int size) {
            return new BodyCheckout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getPaymentID());
        parcel.writeInt(getAddressID());
        parcel.writeTypedList(getBodyOrderBrandList());
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public List<BodyOrderBrand> getBodyOrderBrandList() {
        return bodyOrderBrandList;
    }

    public void setBodyOrderBrandList(List<BodyOrderBrand> bodyOrderBrandList) {
        this.bodyOrderBrandList = bodyOrderBrandList;
    }
}
