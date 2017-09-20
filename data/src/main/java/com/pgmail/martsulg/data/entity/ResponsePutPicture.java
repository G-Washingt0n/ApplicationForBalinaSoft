package com.pgmail.martsulg.data.entity;

/**
 * Created by lenovo on 18.09.2017.
 */

public class ResponsePutPicture implements DataModel{

    private long date;
    private int id;
    private double lat;
    private double lng;
    private String url;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
