package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductMixMatch implements Parcelable {
    private String slug;
    @SerializedName("mix_and_match_image")
    private String imageURL;

    protected ProductMixMatch(Parcel in) {
        setSlug(in.readString());
        setImageURL(in.readString());
    }

    public static final Creator<ProductMixMatch> CREATOR = new Creator<ProductMixMatch>() {
        @Override
        public ProductMixMatch createFromParcel(Parcel in) {
            return new ProductMixMatch(in);
        }

        @Override
        public ProductMixMatch[] newArray(int size) {
            return new ProductMixMatch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getSlug());
        parcel.writeString(getImageURL());
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
