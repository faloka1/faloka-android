package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private Payment payment;
    private Address address;
    private Product product;
    private Checkout checkout;
    private Courier courier;

    public Order(){

    }

    protected Order(Parcel in) {
        setPayment(in.readParcelable(Payment.class.getClassLoader()));
        setAddress(in.readParcelable(Address.class.getClassLoader()));
        setProduct(in.readParcelable(Product.class.getClassLoader()));
        setCheckout(in.readParcelable(Checkout.class.getClassLoader()));
        setCourier(in.readParcelable(Courier.class.getClassLoader()));
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
        parcel.writeParcelable(getPayment(), i);
        parcel.writeParcelable(getAddress(), i);
        parcel.writeParcelable(getProduct(), i);
        parcel.writeParcelable(getCheckout(), i);
        parcel.writeParcelable(getCourier(), i);
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
