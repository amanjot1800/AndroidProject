package com.example.androidgroupproject;


public class BBCHeadline {
    private String title;
    private String description;
    private String link;
    private String dateOfArticle;
    private long id = 1L;


    /**
     * This constructor sets the required values. This can be used as a ArrayList to store all the
     * Articles based on @title, @description, @link and @dateOfArticle
     * @param title of the article
     * @param description of the article
     * @param link of the article
     * @param dateOfArticle that was published
     */
    BBCHeadline(String title, String description, String link , String dateOfArticle ){
        this.title= title;
        this.description = description;
        this.link = link;
        this.dateOfArticle = dateOfArticle;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateOfArticle() {
        return dateOfArticle;
    }

    public String getLink(){ return link; }

    public long getId() { return id; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setLink(String link) { this.link = link; }

    public void setDateOfArticle(String dateOfArticle) { this.dateOfArticle = dateOfArticle; }

    public void setId(long id) { this.id = id; }
}
