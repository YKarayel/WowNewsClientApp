package com.nexuswawe.wownews;

import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WowheadNews {

    private String title;
    private String body;
    private String imageUrl;
    private String postUrl;
    private String dateStr;
    private String postId;
    private String tag;

    public WowheadNews(String postId, String title, String body, String imageUrl, String postUrl, String dateStr, String tag) {
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.postUrl = postUrl;
        this.dateStr = dateStr;
        this.tag = tag;

    }

    // Getter ve Setter metotları buraya eklenebilir


    @Override
    public String toString() {
        return "postId='" + postId + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \nbody='" + body + '\'' +
                ", \nimageUrl='" + imageUrl + '\'' +
                ", \npostUrl='" + postUrl + '\'' +
                ", \ndateStr='" + dateStr + '\'' +
                ", \ntag='" + tag + '\'' +
                '}';
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getDateInMillis() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 'at' hh:mm a", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }


}

