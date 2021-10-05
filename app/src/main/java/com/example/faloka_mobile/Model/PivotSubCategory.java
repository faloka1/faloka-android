package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PivotSubCategory implements Parcelable {
    @SerializedName("category_id")
    private int categoryID;
    @SerializedName("subcategory_id")
    private int subCategoryID;
    @SerializedName("image_url")
    private String imageURL;


    protected PivotSubCategory(Parcel in) {
        setCategoryID(in.readInt());
        setSubCategoryID(in.readInt());
        setImageURL(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getCategoryID());
        dest.writeInt(getSubCategoryID());
        dest.writeString(getImageURL());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PivotSubCategory> CREATOR = new Creator<PivotSubCategory>() {
        @Override
        public PivotSubCategory createFromParcel(Parcel in) {
            return new PivotSubCategory(in);
        }

        @Override
        public PivotSubCategory[] newArray(int size) {
            return new PivotSubCategory[size];
        }
    };

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(int subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
