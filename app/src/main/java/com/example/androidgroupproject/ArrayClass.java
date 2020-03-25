package com.example.androidgroupproject;

public class ArrayClass {


    private String longitude;
    private String latitude;
    private long id;
    private String date;

    public ArrayClass(String longitude, String latitude ,  String date, long id){
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
        this.date = date;

    }
    public ArrayClass(String lat,String longi, String date) {
        this(lat, longi , date,0);
    }

    public  String getLatitude() {
        return latitude;
    }

    public  String getLongitude() {
        return longitude;
    }

    public long getId() {
        return id;
    }

    public  String getDate() {
        return date;
    }
    //shubham
}

