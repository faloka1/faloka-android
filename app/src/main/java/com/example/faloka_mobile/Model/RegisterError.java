package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RegisterError implements Parcelable {

	private List<String> password;
	private List<String> phoneNumber;
	private List<String> email;


	protected RegisterError(Parcel in) {
		password = in.createStringArrayList();
		phoneNumber = in.createStringArrayList();
		email = in.createStringArrayList();
	}

	public static final Creator<RegisterError> CREATOR = new Creator<RegisterError>() {
		@Override
		public RegisterError createFromParcel(Parcel in) {
			return new RegisterError(in);
		}

		@Override
		public RegisterError[] newArray(int size) {
			return new RegisterError[size];
		}
	};

	public void setPassword(List<String> password){
		this.password = password;
	}

	public List<String> getPassword(){
		return password;
	}

	public void setPhoneNumber(List<String> phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public List<String> getPhoneNumber(){
		return phoneNumber;
	}

	public void setEmail(List<String> email){
		this.email = email;
	}

	public List<String> getEmail(){
		return email;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeStringList(password);
		parcel.writeStringList(phoneNumber);
		parcel.writeStringList(email);
	}
}