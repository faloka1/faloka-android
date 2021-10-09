package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {
    private int id;
    private String name;
    @SerializedName("phone_number")
    private String phone;
    private String province;
    private String district;
    @SerializedName("sub_district")
    private String subDistrict;
    @SerializedName("postal_code")
    private int postalCode;
    private String location;
    private PivotAddress pivot;

    protected Address(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setPhone(in.readString());
        setProvince(in.readString());
        setDistrict(in.readString());
        setSubDistrict(in.readString());
        setPostalCode(in.readInt());
        setLocation(in.readString());
        setPivot(in.readParcelable(PivotAddress.class.getClassLoader()));
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getPhone());
        parcel.writeString(getProvince());
        parcel.writeString(getDistrict());
        parcel.writeString(getSubDistrict());
        parcel.writeInt(getPostalCode());
        parcel.writeString(getLocation());
        parcel.writeParcelable(getPivot(), i);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PivotAddress getPivot() {
        return pivot;
    }

    public void setPivot(PivotAddress pivot) {
        this.pivot = pivot;
    }
}
