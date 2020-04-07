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

    /**
     * sets id
     * @param id Long id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * sets sectionName
     * @param sectionName String sectionName
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * sets Title
     * @param title String title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets url
     * @param url String url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return url
     */
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
