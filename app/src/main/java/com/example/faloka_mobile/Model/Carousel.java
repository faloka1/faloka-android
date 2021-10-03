package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Carousel implements Parcelable {
    private int id;
    @SerializedName("image_url")
    private String imageURL;
    @SerializedName("category_id")
    private int categoryID;

    protected Carousel(Parcel in) {
        setId(in.readInt());
        setImageURL(in.readString());
        setCategoryID(in.readInt());
    }

    public static final Creator<Carousel> CREATOR = new Creator<Carousel>() {
        @Override
        public Carousel createFromParcel(Parcel in) {
            return new Carousel(in);
        }

        @Override
        public Carousel[] newArray(int size) {
            return new Carousel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getImageURL());
        parcel.writeInt(getCategoryID());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
