package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SubCategory implements Parcelable {

    public static final String EXTRA_SUBCATEGORY = "extra_subcategory";

    private int id;
    private String slugCategory;
    private String name;
    private String slug;
    @SerializedName("pivot")
    private PivotSubCategory pivotSubCategory;


    protected SubCategory(Parcel in) {
        setId(in.readInt());
        setSlugCategory(in.readString());
        setName(in.readString());
        setSlug(in.readString());
        setPivotSubCategory(in.readParcelable(PivotSubCategory.class.getClassLoader()));
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
        parcel.writeString(getSlugCategory());
        parcel.writeString(getName());
        parcel.writeString(getSlug());
        parcel.writeParcelable(getPivotSubCategory(), i);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlugCategory() {
        return slugCategory;
    }

    public void setSlugCategory(String slugCategory) {
        this.slugCategory = slugCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public PivotSubCategory getPivotSubCategory() {
        return pivotSubCategory;
    }

    public void setPivotSubCategory(PivotSubCategory pivotSubCategory) {
        this.pivotSubCategory = pivotSubCategory;
    }
}
