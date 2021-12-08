package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Login implements Parcelable {
    private String email;
    private String password;

    public Login(){

    }

    protected Login(Parcel in) {
        setEmail(in.readString());
        setPassword(in.readString());
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getEmail());
        parcel.writeString(getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
