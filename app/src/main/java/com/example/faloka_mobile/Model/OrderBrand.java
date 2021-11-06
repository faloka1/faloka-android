package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderBrand implements Parcelable {
    private int id;
    @SerializedName("order_id")
    private int orderID;
    @SerializedName("brand_id")
    private int brandID;
    @SerializedName("shipping_id")
    private int shippingID;
    @SerializedName("shipping")
    private OrderShipping orderShipping;
    @SerializedName("order_details")
    private List<OrderDetail> orderDetailList;

    protected OrderBrand(Parcel in) {
        setId(in.readInt());
        setOrderID(in.readInt());
        setBrandID(in.readInt());
        setShippingID(in.readInt());
        setOrderShipping(in.readParcelable(OrderShipping.class.getClassLoader()));
        setOrderDetailList(in.createTypedArrayList(OrderDetail.CREATOR));
    }

    public static final Creator<OrderBrand> CREATOR = new Creator<OrderBrand>() {
        @Override
        public OrderBrand createFromParcel(Parcel in) {
            return new OrderBrand(in);
        }

        @Override
        public OrderBrand[] newArray(int size) {
            return new OrderBrand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeInt(getOrderID());
        parcel.writeInt(getBrandID());
        parcel.writeInt(getShippingID());
        parcel.writeParcelable(getOrderShipping(), i);
        parcel.writeTypedList(getOrderDetailList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public int getShippingID() {
        return shippingID;
    }

    public void setShippingID(int shippingID) {
        this.shippingID = shippingID;
    }

    public OrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
