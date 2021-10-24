package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Checkout implements Parcelable {

    public static final String EXTRA_CHECKOUT = "EXTRA_CHECKOUT";

    @SerializedName("shipping_price")
    private int shippingPrice;
    @SerializedName("expedition_name")
    private String expeditionName;
    @SerializedName("service")
    private String serviceExpedition;
    @SerializedName("payment_id")
    private int paymentID;
    @SerializedName("address_id")
    private int addressID;
    private int quantity;
    @SerializedName("product_id")
    private int productID;
    @SerializedName("variant_id")
    private int variantID;

    public Checkout(){

    }

    protected Checkout(Parcel in) {
        setShippingPrice(in.readInt());
        setExpeditionName(in.readString());
        setServiceExpedition(in.readString());
        setPaymentID(in.readInt());
        setAddressID(in.readInt());
        setQuantity(in.readInt());
        setProductID(in.readInt());
        setVariantID(in.readInt());
    }

    public static final Creator<Checkout> CREATOR = new Creator<Checkout>() {
        @Override
        public Checkout createFromParcel(Parcel in) {
            return new Checkout(in);
        }

        @Override
        public Checkout[] newArray(int size) {
            return new Checkout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getShippingPrice());
        parcel.writeString(getExpeditionName());
        parcel.writeString(getServiceExpedition());
        parcel.writeInt(getPaymentID());
        parcel.writeInt(getAddressID());
        parcel.writeInt(getQuantity());
        parcel.writeInt(getProductID());
        parcel.writeInt(getVariantID());
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

    public String getServiceExpedition() {
        return serviceExpedition;
    }

    public void setServiceExpedition(String serviceExpedition) {
        this.serviceExpedition = serviceExpedition;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }
}
