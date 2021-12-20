package com.example.faloka_mobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InspireMeResponse implements Parcelable {
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("data")
    private List<InspireMe> inspireMeList;
    @SerializedName("first_page_url")
    private String firstPageURL;
    private int from;
    @SerializedName("last_page")
    private int lastPage;
    @SerializedName("last_page_url")
    private String lastPageURL;
    @SerializedName("next_page_url")
    private String nextPageURL;
    @SerializedName("per_page")
    private int eachPage;
    @SerializedName("prev_page_url")
    private String prevPageURL;
    private int to;
    private int total;

    public InspireMeResponse(){

    }

    protected InspireMeResponse(Parcel in) {
        currentPage = in.readInt();
        inspireMeList = in.createTypedArrayList(InspireMe.CREATOR);
        firstPageURL = in.readString();
        from = in.readInt();
        lastPage = in.readInt();
        lastPageURL = in.readString();
        nextPageURL = in.readString();
        eachPage = in.readInt();
        prevPageURL = in.readString();
        to = in.readInt();
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentPage);
        dest.writeTypedList(inspireMeList);
        dest.writeString(firstPageURL);
        dest.writeInt(from);
        dest.writeInt(lastPage);
        dest.writeString(lastPageURL);
        dest.writeString(nextPageURL);
        dest.writeInt(eachPage);
        dest.writeString(prevPageURL);
        dest.writeInt(to);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InspireMeResponse> CREATOR = new Creator<InspireMeResponse>() {
        @Override
        public InspireMeResponse createFromParcel(Parcel in) {
            return new InspireMeResponse(in);
        }

        @Override
        public InspireMeResponse[] newArray(int size) {
            return new InspireMeResponse[size];
        }
    };

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<InspireMe> getInspireMeList() {
        return inspireMeList;
    }

    public void setInspireMeList(List<InspireMe> inspireMeList) {
        this.inspireMeList = inspireMeList;
    }

    public String getFirstPageURL() {
        return firstPageURL;
    }

    public void setFirstPageURL(String firstPageURL) {
        this.firstPageURL = firstPageURL;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getLastPageURL() {
        return lastPageURL;
    }

    public void setLastPageURL(String lastPageURL) {
        this.lastPageURL = lastPageURL;
    }

    public String getNextPageURL() {
        return nextPageURL;
    }

    public void setNextPageURL(String nextPageURL) {
        this.nextPageURL = nextPageURL;
    }

    public int getEachPage() {
        return eachPage;
    }

    public void setEachPage(int eachPage) {
        this.eachPage = eachPage;
    }

    public String getPrevPageURL() {
        return prevPageURL;
    }

    public void setPrevPageURL(String prevPageURL) {
        this.prevPageURL = prevPageURL;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
