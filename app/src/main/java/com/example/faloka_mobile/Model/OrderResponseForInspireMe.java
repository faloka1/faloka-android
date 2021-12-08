package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderResponseForInspireMe implements Parcelable {
	private int quantity;
	private int variantId;
	private String updatedAt;
	private int productId;
	private String createdAt;
	private Integer id;
	private Variant variants;
	private int orderBrandId;
	private int orderId;
	private Product products;

	protected OrderResponseForInspireMe(Parcel in) {
		quantity = in.readInt();
		variantId = in.readInt();
		updatedAt = in.readString();
		productId = in.readInt();
		createdAt = in.readString();
		id = in.readInt();
		orderBrandId = in.readInt();
		orderId = in.readInt();
		products = in.readParcelable(Product.class.getClassLoader());
		variants = in.readParcelable(Variant.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(quantity);
		dest.writeInt(variantId);
		dest.writeString(updatedAt);
		dest.writeInt(productId);
		dest.writeString(createdAt);
		dest.writeInt(id);
		dest.writeInt(orderBrandId);
		dest.writeInt(orderId);
		dest.writeParcelable(products,flags);
		dest.writeParcelable(variants,flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<OrderResponseForInspireMe> CREATOR = new Creator<OrderResponseForInspireMe>() {
		@Override
		public OrderResponseForInspireMe createFromParcel(Parcel in) {
			return new OrderResponseForInspireMe(in);
		}

		@Override
		public OrderResponseForInspireMe[] newArray(int size) {
			return new OrderResponseForInspireMe[size];
		}
	};

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setVariantId(int variantId){
		this.variantId = variantId;
	}

	public int getVariantId(){
		return variantId;
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

	public Integer getId(){
		return id;
	}

	public void setVariants(Variant variants){
		this.variants = variants;
	}

	public Variant getVariants(){
		return variants;
	}

	public void setOrderBrandId(int orderBrandId){
		this.orderBrandId = orderBrandId;
	}

	public int getOrderBrandId(){
		return orderBrandId;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setProducts(Product products){
		this.products = products;
	}

	public Product getProducts(){
		return products;
	}
}
