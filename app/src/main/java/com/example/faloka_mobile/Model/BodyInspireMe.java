package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BodyInspireMe implements Parcelable {

	@SerializedName("caption")
	private String caption;

	@SerializedName("title")
	private String title;

	@SerializedName("products")
	private List<BodyProductsItem> products;


	public BodyInspireMe(){}
	protected BodyInspireMe(Parcel in) {
		caption = in.readString();
		title = in.readString();
		products = in.createTypedArrayList(BodyProductsItem.CREATOR);
	}

	public static final Creator<BodyInspireMe> CREATOR = new Creator<BodyInspireMe>() {
		@Override
		public BodyInspireMe createFromParcel(Parcel in) {
			return new BodyInspireMe(in);
		}

		@Override
		public BodyInspireMe[] newArray(int size) {
			return new BodyInspireMe[size];
		}
	};

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setProducts(List<BodyProductsItem> products){
		this.products = products;
	}

	public List<BodyProductsItem> getProducts(){
		return products;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(caption);
		parcel.writeString(title);
		parcel.writeTypedList(products);
	}
}