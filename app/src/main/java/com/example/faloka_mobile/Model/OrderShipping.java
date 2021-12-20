package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderShipping implements Parcelable {
    private int id;
    @SerializedName("shipping_price")
    private int shippingPrice;
    @SerializedName("expedition_name")
    private String expeditionName;
    private String service;

    protected OrderShipping(Parcel in) {
        setId(in.readInt());
        setShippingPrice(in.readInt());
        setExpeditionName(in.readString());
        setService(in.readString());
    }

    public static final Creator<OrderShipping> CREATOR = new Creator<OrderShipping>() {
        @Override
        public OrderShipping createFromParcel(Parcel in) {
            return new OrderShipping(in);
        }

        @Override
        public OrderShipping[] newArray(int size) {
            return new OrderShipping[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeInt(getShippingPrice());
        parcel.writeString(getExpeditionName());
        parcel.writeString(getService());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
