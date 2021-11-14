package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Brand implements Parcelable {
	private int id;
	private String name;

	public Brand(){

	}

	protected Brand(Parcel in) {
		id = in.readInt();
		name = in.readString();
	}

	public static final Creator<Brand> CREATOR = new Creator<Brand>() {
		@Override
		public Brand createFromParcel(Parcel in) {
			return new Brand(in);
		}

		@Override
		public Brand[] newArray(int size) {
			return new Brand[size];
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



	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(id);
		parcel.writeString(name);
	}

	@Override public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Brand other = (Brand)obj;

		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}
}