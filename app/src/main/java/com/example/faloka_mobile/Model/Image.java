package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
     private int id;
     private String name;

    public Image(String name, int position){
        this.name = name;
        this.position = position;
    }
    protected Image(Parcel in) {
        id = in.readInt();
        name = in.readString();
        position = in.readInt();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(position);
    }
}
