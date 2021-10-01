package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SubCategory implements Parcelable {

    private int id;
    private String name;
    private Image image;

    public SubCategory(String name, Image image){
        this.name = name;
        this.image = image;
    }

    public SubCategory(){

    }

    protected SubCategory(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
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
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeParcelable(image, i);
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
