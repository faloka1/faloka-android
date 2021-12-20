package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PivotProduct implements Parcelable {
    @SerializedName("category_id")
    private int categoryID;
    @SerializedName("product_id")
    private int productID;

    protected PivotProduct(Parcel in) {
        setCategoryID(in.readInt());
        setProductID(in.readInt());
    }

    public static final Creator<PivotProduct> CREATOR = new Creator<PivotProduct>() {
        @Override
        public PivotProduct createFromParcel(Parcel in) {
            return new PivotProduct(in);
        }

        @Override
        public PivotProduct[] newArray(int size) {
            return new PivotProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getCategoryID());
        parcel.writeInt(getProductID());
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
