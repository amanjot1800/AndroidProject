package com.example.androidgroupproject;

public class Article {
    private String title;
    private String url;
    private String sectionName;
    private Long id;

    public Article(String title, String url, String sectionName, Long id) {
        this.title = title;
        this.url = url;
        this.sectionName = sectionName;
        this.id = id;
    }

//    public Article(String title, String url, String sectionName) {
//        this.title = title;
//        this.url = url;
//        this.sectionName = sectionName;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", sectionName='" + sectionName + '\'' +
                '}';
    }
}
