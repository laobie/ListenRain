package com.jaeger.listenrain.entity;

import java.io.Serializable;

/**
 * Created by Jaeger on 15/9/29.
 * ListenRain
 */
public class Article implements Serializable {
    private String title;
    private String coverUrl;
    private String date;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article(String title, String content, String coverUrl, String date) {

        this.title = title;
        this.content = content;
        this.coverUrl = coverUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
