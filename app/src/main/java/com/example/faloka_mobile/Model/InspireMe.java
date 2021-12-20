package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class InspireMe implements Parcelable {

    @SerializedName("inspiremeproducts")
    private ArrayList<InspiremeProducts> inspiremeproducts;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("caption")
    private String caption;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    public void setInspiremeproducts(ArrayList<InspiremeProducts> inspiremeproducts) {
        this.inspiremeproducts = inspiremeproducts;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SerializedName("title")
    private String title;

    @SerializedName("user")
    private User user;

    protected InspireMe(Parcel in) {
        setInspiremeproducts(in.createTypedArrayList(InspiremeProducts.CREATOR));
        setUpdatedAt(in.readString());
        setUserId(in.readInt());
        setImageUrl(in.readString());
        setCaption(in.readString());
        setCreatedAt(in.readString());
        setId(in.readInt());
        setTitle(in.readString());
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

    public ArrayList<InspiremeProducts> getInspiremeproducts(){
        return inspiremeproducts;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public int getUserId(){
        return userId;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCaption(){
        return caption;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public User getUser(){
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(getInspiremeproducts());
        parcel.writeString(getUpdatedAt());
        parcel.writeInt(getUserId());
        parcel.writeString(getImageUrl());
        parcel.writeString(getCaption());
        parcel.writeString(getCreatedAt());
        parcel.writeInt(getId());
        parcel.writeString(getTitle());
    }
}