package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable{
	private int id;
	private String name;
	private String email;
	@SerializedName("phone_number")
	private String phone;
	private String gender;
	@SerializedName("photo_profile_url")
	private String imageProfile;
	@SerializedName("addresses")
	private List<Address> addressList;


	protected User(Parcel in) {
		setId(in.readInt());
		setEmail(in.readString());
		setName(in.readString());
		setPhone(in.readString());
		setGender(in.readString());
		setImageProfile(in.readString());
		setAddressList(in.createTypedArrayList(Address.CREATOR));
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(getId());
		parcel.writeString(getEmail());
		parcel.writeString(getName());
		parcel.writeString(getPhone());
		parcel.writeString(getGender());
		parcel.writeString(getImageProfile());
		parcel.writeTypedList(getAddressList());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(String imageProfile) {
		this.imageProfile = imageProfile;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
}