package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {

    public  static final String EXTRA_ORDER = "EXTRA_ORDER";

    private int orderID;
    private Payment payment;
    private Address address;
    private Product product;
    private Checkout checkout;
    private Courier courier;
    private CourierService courierService;
    private int totalOrder;

    public Order(){

    }

    protected Order(Parcel in) {
        setOrderID(in.readInt());
        setPayment(in.readParcelable(Payment.class.getClassLoader()));
        setAddress(in.readParcelable(Address.class.getClassLoader()));
        setProduct(in.readParcelable(Product.class.getClassLoader()));
        setCheckout(in.readParcelable(Checkout.class.getClassLoader()));
        setCourier(in.readParcelable(Courier.class.getClassLoader()));
        setCourierService(in.readParcelable(CourierService.class.getClassLoader()));
        setTotalOrder(in.readInt());
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
        parcel.writeInt(getOrderID());
        parcel.writeParcelable(getPayment(), i);
        parcel.writeParcelable(getAddress(), i);
        parcel.writeParcelable(getProduct(), i);
        parcel.writeParcelable(getCheckout(), i);
        parcel.writeParcelable(getCourier(), i);
        parcel.writeParcelable(getCourierService(), i);
        parcel.writeInt(getTotalOrder());
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

    public CourierService getCourierService() {
        return courierService;
    }

    public void setCourierService(CourierService courierService) {
        this.courierService = courierService;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }
}
