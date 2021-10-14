package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class District implements Parcelable {
    @SerializedName("city_id")
    private int id;
    @SerializedName("province_id")
    private int provinceID;
    private String province;
    private String type;
    @SerializedName("city_name")
    private String name;
    @SerializedName("postal_code")
    private int postalCode;

    protected District(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setProvinceID(in.readInt());
        setProvince(in.readString());
        setType(in.readString());
        setPostalCode(in.readInt());
    }

    public static final Creator<District> CREATOR = new Creator<District>() {
        @Override
        public District createFromParcel(Parcel in) {
            return new District(in);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
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
        parcel.writeInt(getProvinceID());
        parcel.writeString(getProvince());
        parcel.writeString(getType());
        parcel.writeInt(getPostalCode());
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

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
    @Override
    public String toString(){
        return this.name;
    }
}
