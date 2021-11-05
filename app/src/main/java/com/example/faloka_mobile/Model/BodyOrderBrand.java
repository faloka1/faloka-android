package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyOrderBrand implements Parcelable {
    @SerializedName("brand_id")
    private int brandID;
    @SerializedName("shipping_price")
    private int shippingPrice;
    @SerializedName("expedition_name")
    private String expeditionName;
    private String service;
    @SerializedName("items")
    private List<BodyOrderItem> bodyOrderItemList;

    public BodyOrderBrand(){

    }

    protected BodyOrderBrand(Parcel in) {
        setBrandID(in.readInt());
        setShippingPrice(in.readInt());
        setExpeditionName(in.readString());
        setService(in.readString());
        setBodyOrderItemList(in.createTypedArrayList(BodyOrderItem.CREATOR));
    }

    public static final Creator<BodyOrderBrand> CREATOR = new Creator<BodyOrderBrand>() {
        @Override
        public BodyOrderBrand createFromParcel(Parcel in) {
            return new BodyOrderBrand(in);
        }

        @Override
        public BodyOrderBrand[] newArray(int size) {
            return new BodyOrderBrand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getBrandID());
        parcel.writeInt(getShippingPrice());
        parcel.writeString(getExpeditionName());
        parcel.writeString(getService());
        parcel.writeTypedList(getBodyOrderItemList());
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
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

    public List<BodyOrderItem> getBodyOrderItemList() {
        return bodyOrderItemList;
    }

    public void setBodyOrderItemList(List<BodyOrderItem> bodyOrderItemList) {
        this.bodyOrderItemList = bodyOrderItemList;
    }
}
