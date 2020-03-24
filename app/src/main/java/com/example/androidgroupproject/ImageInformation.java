package com.example.androidgroupproject;

public class ImageInformation {

    private String url;
    private String hdurl;
    private String date;
    private long id;

    ImageInformation(String date, String url, String hdurl){
        this(date, url, hdurl, 0);
    }

    ImageInformation(String date, String url, String hdurl, long id){
        this.date = date;
        this.url = url;
        this.hdurl = hdurl;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }
}
