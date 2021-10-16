package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Province implements Parcelable {

    private int id;
    @SerializedName("row")
    private int index;
    @SerializedName("province_id")
    private int provinceID;
    @SerializedName("district_id")
    private int districtID;
    @SerializedName("title")
    private String name;


    protected Province(Parcel in) {
        setId(in.readInt());
        setIndex(in.readInt());
        setProvinceID(in.readInt());
        setDistrictID(in.readInt());
        setName(in.readString());
    }

    public static final Creator<Province> CREATOR = new Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel in) {
            return new Province(in);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeInt(getIndex());
        parcel.writeInt(getProvinceID());
        parcel.writeInt(getDistrictID());
        parcel.writeString(getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return this.name;
    }
}
