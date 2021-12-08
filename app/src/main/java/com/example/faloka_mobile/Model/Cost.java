package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cost implements Parcelable {
    private int value;
    private String etd;
    private String note;

    public Cost(){

    }

    protected Cost(Parcel in) {
        setValue(in.readInt());
        setEtd(in.readString());
        setNote(in.readString());
    }

    public static final Creator<Cost> CREATOR = new Creator<Cost>() {
        @Override
        public Cost createFromParcel(Parcel in) {
            return new Cost(in);
        }

        @Override
        public Cost[] newArray(int size) {
            return new Cost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getValue());
        parcel.writeString(getEtd());
        parcel.writeString(getNote());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getEtd() {
        return etd;
    }

    public void setEtd(String etd) {
        this.etd = etd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
