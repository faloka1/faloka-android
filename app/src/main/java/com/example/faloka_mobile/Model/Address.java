package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String recipientName;
    private String phoneNumber;
    private String province;
    private String city;
    private String district;
    private String postalCode;
    private String completeAddress;

    public Address(){

    }
    protected Address(Parcel in) {
        recipientName = in.readString();
        phoneNumber = in.readString();
        province = in.readString();
        city = in.readString();
        district = in.readString();
        postalCode = in.readString();
        completeAddress = in.readString();
    }
    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(recipientName);
        parcel.writeString(phoneNumber);
        parcel.writeString(province);
        parcel.writeString(city);
        parcel.writeString(district);
        parcel.writeString(postalCode);
        parcel.writeString(completeAddress);

    }
}
