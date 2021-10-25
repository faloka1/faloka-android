package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class InspireMe implements Parcelable {


    public InspireMe(int id, String username, String caption) {
        this.id = id;
        this.username = username;
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    private String profileImageUrl;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    private String caption;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    protected InspireMe(Parcel in) {
        setId(in.readInt());
        setImageUrl(in.readString());
        setProfileImageUrl(in.readString());
        setUsername(in.readString());
        setCaption(in.readString());
    }

    public static final Creator<InspireMe> CREATOR = new Creator<InspireMe>() {
        @Override
        public InspireMe createFromParcel(Parcel in) {
            return new InspireMe(in);
        }

        @Override
        public InspireMe[] newArray(int size) {
            return new InspireMe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getImageUrl());
        parcel.writeString(getProfileImageUrl());
        parcel.writeString(getCaption());
        parcel.writeString(getUsername());
    }


}
