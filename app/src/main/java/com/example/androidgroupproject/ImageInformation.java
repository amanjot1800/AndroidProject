package com.example.androidgroupproject;

/**
 * This Class holds the image information
 */
public class ImageInformation {

    /**
     * The standard url for the image
     */
    private String url;
    /**
     * The high definition url for the image
     */
    private String hdurl;
    /**
     * The date for which urls are downloaded
     */
    private String date;
    /**
     * The database id of the stored information
     */
    private long id;

    /**
     * Constructor to make a ImageInformation Object
     * @param date the date for the image info
     * @param url standard url
     * @param hdurl high definition url
     */
    ImageInformation(String date, String url, String hdurl){
        this(date, url, hdurl, 0);
    }

    /**
     * Constructor to make a ImageInformation Object
     * @param date the date for the image info
     * @param url standard url
     * @param hdurl high definition url
     * @param id the database id of the data stored
     */
    ImageInformation(String date, String url, String hdurl, long id){
        this.date = date;
        this.url = url;
        this.hdurl = hdurl;
        this.id = id;
    }

    /**
     * Getter for url
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter for hdurl
     * @return hdurl
     */
    public String getHdurl() {
        return hdurl;
    }

    /**
     * Getter for date
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for id
     * @return id
     */
    public long getId() {
        return id;
    }
}
