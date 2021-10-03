package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SubCategory implements Parcelable {

    private int id;
    private String slugCategory;
    private String name;
    @SerializedName("image_url")
    private String imageURL;
    private String slug;
    private Pivot pivot;

    protected SubCategory(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setImageURL(in.readString());
        setSlug(in.readString());
        setPivot(in.readParcelable(Pivot.class.getClassLoader()));
    }

    public static final Creator<SubCategory> CREATOR = new Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
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
        parcel.writeString(getImageURL());
        parcel.writeString(getSlug());
        parcel.writeParcelable(getPivot(), i);
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public String getSlugCategory() {
        return slugCategory;
    }

    public void setSlugCategory(String slugCategory) {
        this.slugCategory = slugCategory;
    }
}
