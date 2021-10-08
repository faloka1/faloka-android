package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Courier implements Parcelable {
    private int id;
    private String name;
    private List<CourierService> courierServiceList;

    public Courier(String name){
        this.name = name;
    }

    protected Courier(Parcel in) {
        setId(in.readInt());
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
        parcel.writeString(getName());
        parcel.writeTypedList(getCourierServiceList());
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

    public List<CourierService> getCourierServiceList() {
        return courierServiceList;
    }

    public void setCourierServiceList(List<CourierService> courierServiceList) {
        this.courierServiceList = courierServiceList;
    }
}
