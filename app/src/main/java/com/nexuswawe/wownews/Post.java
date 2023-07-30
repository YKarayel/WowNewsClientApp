package com.nexuswawe.wownews;

public class Post {
    private String title;
    private String imageUrl;
    private String postUrl;
    private String date;

    public Post(String title, String imageUrl, String postUrl, String date) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.postUrl = postUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
