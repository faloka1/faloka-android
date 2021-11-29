package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaymentUploadResponse implements Parcelable {
    private String message;
    @SerializedName("image_payment_url")
    private String imagePaymentURL;

    protected PaymentUploadResponse(Parcel in) {
        setMessage(in.readString());
        setImagePaymentURL(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getMessage());
        dest.writeString(getImagePaymentURL());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaymentUploadResponse> CREATOR = new Creator<PaymentUploadResponse>() {
        @Override
        public PaymentUploadResponse createFromParcel(Parcel in) {
            return new PaymentUploadResponse(in);
        }

        @Override
        public PaymentUploadResponse[] newArray(int size) {
            return new PaymentUploadResponse[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImagePaymentURL() {
        return imagePaymentURL;
    }

    public void setImagePaymentURL(String imagePaymentURL) {
        this.imagePaymentURL = imagePaymentURL;
    }
}
