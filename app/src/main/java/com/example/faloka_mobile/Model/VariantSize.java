package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VariantSize implements Parcelable {

    String name;
    int id;
    @SerializedName("variant_id")
    int variantID;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAtl;

    public VariantSize(){

    }

    protected VariantSize(Parcel in) {
        name = in.readString();
        id = in.readInt();
        variantID = in.readInt();
        createdAt = in.readString();
        updatedAtl = in.readString();
    }

    public static final Creator<VariantSize> CREATOR = new Creator<VariantSize>() {
        @Override
        public VariantSize createFromParcel(Parcel in) {
            return new VariantSize(in);
        }

        @Override
        public VariantSize[] newArray(int size) {
            return new VariantSize[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAtl() {
        return updatedAtl;
    }

    public void setUpdatedAtl(String updatedAtl) {
        this.updatedAtl = updatedAtl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeInt(variantID);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAtl);
    }
}
