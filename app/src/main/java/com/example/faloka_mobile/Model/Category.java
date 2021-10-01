package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category implements Parcelable {

    private int id;
    private String name;
    @SerializedName("carousels")
    private List<Image> images;
    @SerializedName("sub_categories")
    private List<SubCategory> subCategories;

    public Category(String name, List<Image> images, List<SubCategory> subCategories){
        this.name = name;
        this.images = images;
        this.subCategories = subCategories;
    }

    public Category(){}

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
        subCategories = in.createTypedArrayList(SubCategory.CREATOR);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(images);
        parcel.writeTypedList(subCategories);
    }
}
