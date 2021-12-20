package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Payment implements Parcelable {

    public static final String EXTRA_PAYMENT = "EXTRA_PAYMENT";

    private int id;
    @SerializedName("payment_name")
    private String paymentName;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("no_rekening")
    private int accountNumber;
    private int priceService;

    public Payment(){

    }
    protected Payment(Parcel in) {
        setId(in.readInt());
        setPaymentName(in.readString());
        setAccountName(in.readString());
        setAccountNumber(in.readInt());
        setPriceService(in.readInt());
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getPaymentName());
        parcel.writeString(getAccountName());
        parcel.writeInt(getAccountNumber());
        parcel.writeInt(getPriceService());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPriceService() {
        return priceService;
    }

    public void setPriceService(int priceService) {
        this.priceService = priceService;
    }
}
