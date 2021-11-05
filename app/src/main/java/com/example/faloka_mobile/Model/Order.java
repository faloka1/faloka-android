package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order implements Parcelable {

    public  static final String EXTRA_ORDER = "EXTRA_ORDER";

    private Address address;
    private Payment payment;
    private List<CartBrand> cartBrandList;

    public Order(){

    }

    protected Order(Parcel in) {
        setAddress(in.readParcelable(Address.class.getClassLoader()));
        setPayment(in.readParcelable(Payment.class.getClassLoader()));
        setCartBrandList(in.createTypedArrayList(CartBrand.CREATOR));
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
        parcel.writeParcelable(getAddress(), i);
        parcel.writeParcelable(getPayment(), i);
        parcel.writeTypedList(getCartBrandList());
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
}
