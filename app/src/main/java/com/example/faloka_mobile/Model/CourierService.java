package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CourierService implements Parcelable {
    private int id;
    private String name;
    private int price;

    public CourierService(String name, int price){
        this.name = name;
        this.price = price;
    }

    protected CourierService(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setPrice(in.readInt());
    }

    public static final Creator<CourierService> CREATOR = new Creator<CourierService>() {
        @Override
        public CourierService createFromParcel(Parcel in) {
            return new CourierService(in);
        }

        @Override
        public CourierService[] newArray(int size) {
            return new CourierService[size];
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
        parcel.writeInt(getPrice());
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
