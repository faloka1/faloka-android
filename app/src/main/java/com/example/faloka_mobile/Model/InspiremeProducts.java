package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InspiremeProducts implements Parcelable {

	@SerializedName("variant_id")
	private int variantId;

	@SerializedName("inspire_me_id")
	private int inspireMeId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("variants")
	private Variant variants;

	@SerializedName("products")
	private Product products;

	protected InspiremeProducts(Parcel in) {
		variantId = in.readInt();
		inspireMeId = in.readInt();
		updatedAt = in.readString();
		productId = in.readInt();
		createdAt = in.readString();
		id = in.readInt();
		variants = in.readParcelable(Variant.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(variantId);
		dest.writeInt(inspireMeId);
		dest.writeString(updatedAt);
		dest.writeInt(productId);
		dest.writeString(createdAt);
		dest.writeInt(id);
		dest.writeParcelable(variants, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<InspiremeProducts> CREATOR = new Creator<InspiremeProducts>() {
		@Override
		public InspiremeProducts createFromParcel(Parcel in) {
			return new InspiremeProducts(in);
		}

		@Override
		public InspiremeProducts[] newArray(int size) {
			return new InspiremeProducts[size];
		}
	};

	public void setVariantId(int variantId){
		this.variantId = variantId;
	}

	public int getVariantId(){
		return variantId;
	}

	public void setInspireMeId(int inspireMeId){
		this.inspireMeId = inspireMeId;
	}

	public int getInspireMeId(){
		return inspireMeId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setVariants(Variant variants){
		this.variants = variants;
	}

	public Variant getVariants(){
		return variants;
	}

	public void setProduct(Product product){
		this.products = products;
	}

	public Product getProduct(){
		return products;
	}
}