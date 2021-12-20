package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class OrderUser implements Parcelable {

    public static final String EXTRA_ORDER_USER = "EXTRA_ORDER_USER";

    private int id;
    @SerializedName("image_payment_url")
    private String imagePaymentURL;
    private String status;
    @SerializedName("address_id")
    private int addressID;
    @SerializedName("payment_id")
    private int paymentID;
    @SerializedName("user_id")
    private int userID;
    @SerializedName("created_at")
    private Date createDate;
    @SerializedName("updated_at")
    private Date updateDate;
    @SerializedName("order_brands")
    private List<OrderBrand> orderBrandList;
    private Address address;
    private Payment payment;

    public OrderUser(){

    }

    protected OrderUser(Parcel in) {
        setId(in.readInt());
        setImagePaymentURL(in.readString());
        setStatus(in.readString());
        setAddressID(in.readInt());
        setPaymentID(in.readInt());
        setUserID(in.readInt());
        setOrderBrandList(in.createTypedArrayList(OrderBrand.CREATOR));
        setAddress(in.readParcelable(Address.class.getClassLoader()));
        setPayment(in.readParcelable(Payment.class.getClassLoader()));
        setCreateDate((Date) in.readSerializable());
        setUpdateDate((Date) in.readSerializable());
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
        parcel.writeString(getStatus());
        parcel.writeInt(getAddressID());
        parcel.writeInt(getPaymentID());
        parcel.writeInt(getUserID());
        parcel.writeTypedList(getOrderBrandList());
        parcel.writeParcelable(getAddress(), i);
        parcel.writeParcelable(getPayment(), i);
        parcel.writeSerializable(getCreateDate());
        parcel.writeSerializable(getUpdateDate());
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<OrderBrand> getOrderBrandList() {
        return orderBrandList;
    }

    public void setOrderBrandList(List<OrderBrand> orderBrandList) {
        this.orderBrandList = orderBrandList;
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
