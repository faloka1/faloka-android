package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyCheckout implements Parcelable {
    @SerializedName("shipping_price")
    private int shippingPrice;
    @SerializedName("expedition_name")
    private String expeditionName;
    private String service;
    @SerializedName("payment_id")
    private int paymentID;
    @SerializedName("address_id")
    private int addressID;
    @SerializedName("orderDetails")
    private List<BodyOrderDetail> bodyOrderDetailList;

    public BodyCheckout(){

    }

    protected BodyCheckout(Parcel in) {
        setShippingPrice(in.readInt());
        setExpeditionName(in.readString());
        setService(in.readString());
        setPaymentID(in.readInt());
        setAddressID(in.readInt());
        setBodyOrderDetailList(in.createTypedArrayList(BodyOrderDetail.CREATOR));
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
        parcel.writeInt(getShippingPrice());
        parcel.writeString(getExpeditionName());
        parcel.writeString(getService());
        parcel.writeInt(getPaymentID());
        parcel.writeInt(getAddressID());
        parcel.writeTypedList(getBodyOrderDetailList());
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(int shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getExpeditionName() {
        return expeditionName;
    }

    public void setExpeditionName(String expeditionName) {
        this.expeditionName = expeditionName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public List<BodyOrderDetail> getBodyOrderDetailList() {
        return bodyOrderDetailList;
    }

    public void setBodyOrderDetailList(List<BodyOrderDetail> bodyOrderDetailList) {
        this.bodyOrderDetailList = bodyOrderDetailList;
    }
}
