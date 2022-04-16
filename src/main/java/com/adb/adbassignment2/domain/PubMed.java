package com.adb.adbassignment2.domain;

import lombok.Builder;

import java.sql.Date;

@Builder
public class PubMed {
    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getFirst_author() {
        return first_author;
    }

    public void setFirst_author(String first_author) {
        this.first_author = first_author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    private Integer pmid;
    private String article_title;
    private String first_author;
    private String publisher;
    private Date publishedDate;
    private String uploader;
}
