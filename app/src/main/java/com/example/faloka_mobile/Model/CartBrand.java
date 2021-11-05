package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CartBrand implements Parcelable {

    public static final String EXTRA_CART_BRAND = "EXTRA_CART_BRAND";

    private Brand brand;
//    private List<Product> productList;
//    private List<Integer> quantityList;
    private Courier courier;
    private CourierService courierService;
    private List<Cart> cartList;

    public CartBrand(){

    }


    protected CartBrand(Parcel in) {
        setBrand(in.readParcelable(Brand.class.getClassLoader()));
        setCourier(in.readParcelable(Courier.class.getClassLoader()));
        setCourierService(in.readParcelable(CourierService.class.getClassLoader()));
        setCartList(in.createTypedArrayList(Cart.CREATOR));
    }

    public static final Creator<CartBrand> CREATOR = new Creator<CartBrand>() {
        @Override
        public CartBrand createFromParcel(Parcel in) {
            return new CartBrand(in);
        }

        @Override
        public CartBrand[] newArray(int size) {
            return new CartBrand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(getBrand(), i);
        parcel.writeParcelable(getCourier(), i);
        parcel.writeParcelable(getCourierService(), i);
        parcel.writeTypedList(getCartList());
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
