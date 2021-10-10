package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentMethod implements Parcelable {
    String bank;
    String rekeningNumber;
    String rekeningName;


    public PaymentMethod(String bank, String rekeningName, String rekeningNumber) {
        this.bank = bank;
        this.rekeningName = rekeningName;
        this.rekeningNumber = rekeningNumber;
    }

    protected PaymentMethod(Parcel in){
        setBank(in.readString());
        setRekeningName(in.readString());
        setRekeningNumber(in.readString());
    }

    public static final Creator<PaymentMethod> CREATOR = new  Creator<PaymentMethod> () {
        @Override
        public PaymentMethod createFromParcel(Parcel in) {
            return new PaymentMethod(in);
        }

        @Override
        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getRekeningNumber() {
        return rekeningNumber;
    }

    public void setRekeningNumber(String rekeningNumber) {
        this.rekeningNumber = rekeningNumber;
    }

    public String getRekeningName() {
        return rekeningName;
    }

    public void setRekeningName(String rekeningName) {
        this.rekeningName = rekeningName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bank);
        parcel.writeString(rekeningName);
        parcel.writeString(rekeningNumber);
    }
}
