package com.example.faloka_mobile.Register;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.faloka_mobile.Model.User;

public class RegisterResponse implements Parcelable {

    private String message;
    private User user;

    protected RegisterResponse(Parcel in) {
        setMessage(in.readString());
        setUser(in.readParcelable(User.class.getClassLoader()));
    }

    public static final Creator<RegisterResponse> CREATOR = new Creator<RegisterResponse>() {
        @Override
        public RegisterResponse createFromParcel(Parcel in) {
            return new RegisterResponse(in);
        }

        @Override
        public RegisterResponse[] newArray(int size) {
            return new RegisterResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
        parcel.writeParcelable(getUser(), i);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
