package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {

    public static final String EXTRA_PRODUCT = "extra_product";

    private int id;
    private String name;
    @SerializedName("variants")
    private List<Variant> variantList;
    @SerializedName("brands")
    private Brand brand;
    private int price;
    @SerializedName("pivot")
    private PivotProduct pivotProduct;
    private String description;
    private float discount;
    private String slug;
    @SerializedName("mix_and_match_image")
    private String imageMixMatchURL;
    private static int quantity;

    public Product(){}
    public Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        variantList = in.createTypedArrayList(Variant.CREATOR);
        brand = in.readParcelable(Brand.class.getClassLoader());
        price = in.readInt();
        pivotProduct = in.readParcelable(PivotProduct.class.getClassLoader());
        description = in.readString();
        discount = in.readFloat();
        slug = in.readString();
        imageMixMatchURL = in.readString();
        setQuantity(in.readInt());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getProductImageURL(){
        if(!variantList.isEmpty()) {
            Variant variant = variantList.get(0);
            List<VariantImage> variantImageList = variant.getVariantImageList();
            VariantImage variantImage = variantImageList.get(0);
            return variantImage.getImageURL();
        }
        return " ";
    }

   public List<VariantImage> getProductCarouselImageURL(){
        List<VariantImage> variantImageList = new ArrayList<>();
        for(Variant variant : variantList){
            List<VariantImage> tempVariantImageList = new ArrayList<>();
            tempVariantImageList = variant.getVariantImageList();
            for(VariantImage variantImage : tempVariantImageList){
                variantImageList.add(variantImage);
            }
        }
        return variantImageList;
   }

   public String getSizeProduct(){
       Variant variant= variantList.get(0);
       return variant.getName();
   }

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

    public List<Variant> getVariantList() {
        return variantList;
    }

    public void setVariantList(List<Variant> variantList) {
        this.variantList = variantList;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PivotProduct getPivotProduct() {
        return pivotProduct;
    }

    public void setPivotProduct(PivotProduct pivotProduct) {
        this.pivotProduct = pivotProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImageMixMatchURL() {
        return imageMixMatchURL;
    }

    public void setImageMixMatchURL(String imageMixMatchURL) {
        this.imageMixMatchURL = imageMixMatchURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(variantList);
        parcel.writeParcelable(brand, i);
        parcel.writeInt(price);
        parcel.writeParcelable(pivotProduct, i);
        parcel.writeString(description);
        parcel.writeFloat(discount);
        parcel.writeString(slug);
        parcel.writeString(imageMixMatchURL);
        parcel.writeInt(getQuantity());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
