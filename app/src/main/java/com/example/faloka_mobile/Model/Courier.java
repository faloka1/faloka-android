package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Courier implements Parcelable {
    public static final String EXTRA_COURIER = "EXTRA_COURIER";
    private int id;
    private String code;
    private String name;
    @SerializedName("costs")
    private List<CourierService> courierServiceList;

    public Courier(){

    }

    protected Courier(Parcel in) {
        setId(in.readInt());
        setCode(in.readString());
        setName(in.readString());
        setCourierServiceList(in.createTypedArrayList(CourierService.CREATOR));
    }

    public static final Creator<Courier> CREATOR = new Creator<Courier>() {
        @Override
        public Courier createFromParcel(Parcel in) {
            return new Courier(in);
        }

        @Override
        public Courier[] newArray(int size) {
            return new Courier[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getCode());
        parcel.writeString(getName());
        parcel.writeTypedList(getCourierServiceList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourierService> getCourierServiceList() {
        return courierServiceList;
    }

    public void setCourierServiceList(List<CourierService> courierServiceList) {
        this.courierServiceList = courierServiceList;
    }
}
