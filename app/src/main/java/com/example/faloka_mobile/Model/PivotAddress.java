package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PivotAddress implements Parcelable {
    @SerializedName("user_id")
    private int userID;
    @SerializedName("address_id")
    private int addressID;

    protected PivotAddress(Parcel in) {
        setUserID(in.readInt());
        setAddressID(in.readInt());
    }

    public static final Creator<PivotAddress> CREATOR = new Creator<PivotAddress>() {
        @Override
        public PivotAddress createFromParcel(Parcel in) {
            return new PivotAddress(in);
        }

        @Override
        public PivotAddress[] newArray(int size) {
            return new PivotAddress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getUserID());
        parcel.writeInt(getAddressID());
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }
}
