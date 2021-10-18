package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    public static final int REQUEST_EDIT_ADDRESS = 100;
    public static final int RESULT_EDIT_ADDRESS = 111;
    public static final int REQUEST_ADD_ADDRESS = 80;
    public static final int RESULT_ADD_ADDRESS = 88;

    private int id;
    @SerializedName("province_id")
    private int provinceID;
    @SerializedName("district_id")
    private int districtID;
    @SerializedName("sub_district")
    private String subDistrict;
    @SerializedName("postal_code")
    private int postalCode;
    private String location;
    private PivotAddress pivot;
    @SerializedName("districts")
    private District district;
    @SerializedName("provinces")
    private Province province;

    public Address(){

    }

    protected Address(Parcel in) {
        setId(in.readInt());
        setProvinceID(in.readInt());
        setDistrictID(in.readInt());
        setSubDistrict(in.readString());
        setPostalCode(in.readInt());
        setLocation(in.readString());
        setPivot(in.readParcelable(PivotAddress.class.getClassLoader()));
        setDistrict(in.readParcelable(District.class.getClassLoader()));
        setProvince(in.readParcelable(Province.class.getClassLoader()));
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeInt(getProvinceID());
        parcel.writeInt(getDistrictID());
        parcel.writeString(getSubDistrict());
        parcel.writeInt(getPostalCode());
        parcel.writeString(getLocation());
        parcel.writeParcelable(getPivot(), i);
        parcel.writeParcelable(getDistrict(), i);
        parcel.writeParcelable(getProvince(), i);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PivotAddress getPivot() {
        return pivot;
    }

    public void setPivot(PivotAddress pivot) {
        this.pivot = pivot;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
