package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserRegister implements Parcelable {
    private String name;
    private String email;
    private String password;
    @SerializedName("password_confirmation")
    private String passwordConfirmation;
    @SerializedName("phone_number")
    private String phone;
    private String gender;

    public UserRegister(){

    }

    protected UserRegister(Parcel in) {
        setName(in.readString());
        setEmail(in.readString());
        setPassword(in.readString());
        setPasswordConfirmation(in.readString());
        setPhone(in.readString());
        setGender(in.readString());
    }

    public static final Creator<UserRegister> CREATOR = new Creator<UserRegister>() {
        @Override
        public UserRegister createFromParcel(Parcel in) {
            return new UserRegister(in);
        }

        @Override
        public UserRegister[] newArray(int size) {
            return new UserRegister[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        parcel.writeString(getEmail());
        parcel.writeString(getPassword());
        parcel.writeString(getPasswordConfirmation());
        parcel.writeString(getPhone());
        parcel.writeString(getGender());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
