package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.Blog;

public class BlogResponseDTO {
    private Long id;
    private String title;
    private String bannerImg;
    private String summary;
    private String bodyText;
    private String authorImg;
    private String authorName;
    private String publishDate;
    private String tag;

    public BlogResponseDTO(Blog entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.bannerImg = entity.getBannerImg();
        this.summary = entity.getSummary();
        this.bodyText = entity.getBodyText();
        this.authorImg = entity.getAuthorImg();
        this.authorName = entity.getAuthorName();
        this.publishDate = entity.getPublishDate();
        this.tag = entity.getTag();
    }

    public BlogResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
