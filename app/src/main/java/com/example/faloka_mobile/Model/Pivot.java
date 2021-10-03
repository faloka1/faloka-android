package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pivot implements Parcelable {
    @SerializedName("category_id")
    private int categoryID;
    @SerializedName("subcategory_id")
    private int subCategoryID;

    protected Pivot(Parcel in) {
        categoryID = in.readInt();
        subCategoryID = in.readInt();
    }

    public static final Creator<Pivot> CREATOR = new Creator<Pivot>() {
        @Override
        public Pivot createFromParcel(Parcel in) {
            return new Pivot(in);
        }

        @Override
        public Pivot[] newArray(int size) {
            return new Pivot[size];
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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(categoryID);
        parcel.writeInt(subCategoryID);
    }
}
