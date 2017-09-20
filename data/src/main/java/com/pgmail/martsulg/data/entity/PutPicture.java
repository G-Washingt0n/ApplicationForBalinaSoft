package com.pgmail.martsulg.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 18.09.2017.
 */

public class PutPicture {

    public static PutPicture instance;

    public static synchronized PutPicture getInstance(){
        if (instance == null) {
            instance = new PutPicture();
        }
        return instance;

    }


    @SerializedName("base64Image")
    private String imageString;
    @SerializedName("date")
    private long date;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    @SerializedName("Access-Token")
    private String TOKEN;

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
