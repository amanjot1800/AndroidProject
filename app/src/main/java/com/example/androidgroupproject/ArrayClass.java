package com.example.androidgroupproject;

public class ArrayClass {


    private String longitude;
    private String latitude;
    private long id;
    private String url;
// Constructor to iniliase the values
    public ArrayClass(String longitude, String latitude , String url,  long id){
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
        this.url =url;

    }
    // chaining constructor
    public ArrayClass(String lat,String longi, String url) {
        this(lat, longi , url, 0);
    }

    public  String getLatitude() { // getter to get the value of the data
        return latitude; // returns the latitude
    }

    public  String getLongitude() {
        return longitude;
    } // to get the value of longitude

    public long getId() {
        return id;
    } // to get the value of ID and return id


    public  String getUrl() {
        return url;
    } // to get the value of the url

    //shubham
}


//Amanjot



