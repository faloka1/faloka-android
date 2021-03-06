package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order implements Parcelable {

    public  static final String EXTRA_ORDER = "EXTRA_ORDER";

    private int id;
    private Address address;
    private Payment payment;
    private List<CartBrand> cartBrandList;
    private String imagePaymentURL;

    public Order(){

    }

    protected Order(Parcel in) {
        setId(in.readInt());
        setAddress(in.readParcelable(Address.class.getClassLoader()));
        setPayment(in.readParcelable(Payment.class.getClassLoader()));
        setCartBrandList(in.createTypedArrayList(CartBrand.CREATOR));
        setImagePaymentURL(in.readString());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeParcelable(getAddress(), i);
        parcel.writeParcelable(getPayment(), i);
        parcel.writeTypedList(getCartBrandList());
        parcel.writeString(getImagePaymentURL());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<CartBrand> getCartBrandList() {
        return cartBrandList;
    }

    public void setCartBrandList(List<CartBrand> cartBrandList) {
        this.cartBrandList = cartBrandList;
    }

    public String getImagePaymentURL() {
        return imagePaymentURL;
    }

    public void setImagePaymentURL(String imagePaymentURL) {
        this.imagePaymentURL = imagePaymentURL;
    }
}
