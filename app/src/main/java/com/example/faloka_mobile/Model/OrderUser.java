package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderUser implements Parcelable {
    private int id;
    @SerializedName("image_payment_url")
    private String imagePaymentURL;
    @SerializedName("shipping_price")
    private int shippingPrice;
    private String service;
    @SerializedName("expedition_name")
    private String expeditionName;
    private String status;
    @SerializedName("address_id")
    private int addressID;
    @SerializedName("payment_id")
    private int paymentID;
    @SerializedName("user_id")
    private int userID;
    @SerializedName("order_details")
    private List<OrderDetail> orderDetailList;
    private Address address;
    private Payment payment;

    protected OrderUser(Parcel in) {
        setId(in.readInt());
        setImagePaymentURL(in.readString());
        setShippingPrice(in.readInt());
        setService(in.readString());
        setExpeditionName(in.readString());
        setStatus(in.readString());
        setAddressID(in.readInt());
        setPaymentID(in.readInt());
        setUserID(in.readInt());
        setOrderDetailList(in.createTypedArrayList(OrderDetail.CREATOR));
        setAddress(in.readParcelable(Address.class.getClassLoader()));
        setPayment(in.readParcelable(Payment.class.getClassLoader()));
    }

    public static final Creator<OrderUser> CREATOR = new Creator<OrderUser>() {
        @Override
        public OrderUser createFromParcel(Parcel in) {
            return new OrderUser(in);
        }

        @Override
        public OrderUser[] newArray(int size) {
            return new OrderUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getImagePaymentURL());
        parcel.writeInt(getShippingPrice());
        parcel.writeString(getService());
        parcel.writeString(getExpeditionName());
        parcel.writeString(getStatus());
        parcel.writeInt(getAddressID());
        parcel.writeInt(getPaymentID());
        parcel.writeInt(getUserID());
        parcel.writeTypedList(getOrderDetailList());
        parcel.writeParcelable(getAddress(), i);
        parcel.writeParcelable(getPayment(), i);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePaymentURL() {
        return imagePaymentURL;
    }

    public void setImagePaymentURL(String imagePaymentURL) {
        this.imagePaymentURL = imagePaymentURL;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(int shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getExpeditionName() {
        return expeditionName;
    }

    public void setExpeditionName(String expeditionName) {
        this.expeditionName = expeditionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
