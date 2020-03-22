package com.example.androidgroupproject;


public class Headline {
    public String title;
    public String description;
    public String dateOfArticle;
    public long id;


    Headline(String title, String description , String dateOfArticle , long id){
        this.title= title;
        this.description = description;
        this.dateOfArticle = dateOfArticle;
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setDateOfArticle(String dateOfArticle) { this.dateOfArticle = dateOfArticle; }

    public void setId(long id) { this.id = id; }
}
