package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VariantImage implements Parcelable {
    private int id;
    @SerializedName("image_url")
    private String imageURL;
    @SerializedName("variant_id")
    private int variantID;

    public VariantImage(String imageURL){
        setImageURL(imageURL);
    }

    protected VariantImage(Parcel in) {
        setId(in.readInt());
        setImageURL(in.readString());
        setVariantID(in.readInt());
    }

    public static final Creator<VariantImage> CREATOR = new Creator<VariantImage>() {
        @Override
        public VariantImage createFromParcel(Parcel in) {
            return new VariantImage(in);
        }

        @Override
        public VariantImage[] newArray(int size) {
            return new VariantImage[size];
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
        parcel.writeInt(getVariantID());
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

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }
}
