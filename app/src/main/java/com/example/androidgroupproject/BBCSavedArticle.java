package com.example.androidgroupproject;


public class BBCSavedArticle {
    private String title;
    private String description;
    private String link;
    private String dateOfArticle;
    private long id;


    /**
     * This constructor gets called as an ArrayList to store all the saved articles.
     * @param title of the article
     * @param description of the article
     * @param link of the article
     * @param dateOfArticle when it was published
     * @param id of the article saved in the database
     */
    BBCSavedArticle(String title, String description, String link , String dateOfArticle, long id ){
        this.title= title;
        this.description = description;
        this.link = link;
        this.dateOfArticle = dateOfArticle;
        this.id = id;
    }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getDateOfArticle() { return dateOfArticle; }

    public String getLink(){ return link; }

    public long getId() { return id; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setLink(String link) { this.link = link; }

    public void setDateOfArticle(String dateOfArticle) { this.dateOfArticle = dateOfArticle; }

    public void setId(long id) { this.id = id; }
}
