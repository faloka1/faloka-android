package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderDetail implements Parcelable {
    private int id;
    private int quantity;
    @SerializedName("order_id")
    private int orderID;
    @SerializedName("variant_id")
    private int variantID;
    @SerializedName("product_id")
    private int productID;
    @SerializedName("variants")
    private Variant variant;
    @SerializedName("products")
    private Product product;

    protected OrderDetail(Parcel in) {
        setId(in.readInt());
        setQuantity(in.readInt());
        setOrderID(in.readInt());
        setVariantID(in.readInt());
        setProductID(in.readInt());
        setVariant(in.readParcelable(Variant.class.getClassLoader()));
        setProduct(in.readParcelable(Product.class.getClassLoader()));
    }

    public static final Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel in) {
            return new OrderDetail(in);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeInt(getQuantity());
        parcel.writeInt(getOrderID());
        parcel.writeInt(getVariantID());
        parcel.writeInt(getProductID());
        parcel.writeParcelable(getVariant(), i);
        parcel.writeParcelable(getProduct(), i);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
