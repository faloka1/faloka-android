package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Logout implements Parcelable {
    private String message;

    protected Logout(Parcel in) {
        setMessage(in.readString());
    }

    public static final Creator<Logout> CREATOR = new Creator<Logout>() {
        @Override
        public Logout createFromParcel(Parcel in) {
            return new Logout(in);
        }

        @Override
        public Logout[] newArray(int size) {
            return new Logout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
