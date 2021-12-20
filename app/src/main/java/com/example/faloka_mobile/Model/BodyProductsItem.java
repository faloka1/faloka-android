package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BodyProductsItem implements Parcelable {

	@SerializedName("variant_id")
	private String variantId;

	@SerializedName("product_id")
	private String productId;

	private String image;

	public BodyProductsItem(){}
	protected BodyProductsItem(Parcel in) {
		variantId = in.readString();
		productId = in.readString();
	}

	public static final Creator<BodyProductsItem> CREATOR = new Creator<BodyProductsItem>() {
		@Override
		public BodyProductsItem createFromParcel(Parcel in) {
			return new BodyProductsItem(in);
		}

		@Override
		public BodyProductsItem[] newArray(int size) {
			return new BodyProductsItem[size];
		}
	};

	public void setVariantId(String variantId){
		this.variantId = variantId;
	}

	public String getVariantId(){
		return variantId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(variantId);
		parcel.writeString(productId);
	}
}