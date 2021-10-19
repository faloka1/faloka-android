package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourierService implements Parcelable {
    public static final String EXTRA_COURIER_SERVICE = "EXTRA_COURIER_SERVICE";
    @SerializedName("service")
    private String name;
    private String description;
    private List<Cost> cost;

    protected CourierService(Parcel in) {
        setName(in.readString());
        setDescription(in.readString());
        setCost(in.createTypedArrayList(Cost.CREATOR));
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
        parcel.writeString(getName());
        parcel.writeString(getDescription());
        parcel.writeTypedList(getCost());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Cost> getCost() {
        return cost;
    }

    public void setCost(List<Cost> cost) {
        this.cost = cost;
    }
}
