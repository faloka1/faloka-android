package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cart implements Parcelable {
    private int id;
    private int quantity;
    @SerializedName("product_id")
    private int productID;
    @SerializedName("variant_id")
    private int variantID;
    @SerializedName("user_id")
    private int userID;
    @SerializedName("variants")
    private Variant variant;
    @SerializedName("products")
    private Product product;

    public Cart(){

    }

    protected Cart(Parcel in) {
        setId(in.readInt());
        setQuantity(in.readInt());
        setProductID(in.readInt());
        setVariantID(in.readInt());
        setUserID(in.readInt());
        setVariant(in.readParcelable(Variant.class.getClassLoader()));
        setProduct(in.readParcelable(Product.class.getClassLoader()));
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
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
        parcel.writeInt(getProductID());
        parcel.writeInt(getVariantID());
        parcel.writeInt(getUserID());
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
