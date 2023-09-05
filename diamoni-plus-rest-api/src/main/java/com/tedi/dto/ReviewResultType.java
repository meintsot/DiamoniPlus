package com.tedi.dto;

public class ReviewResultType {

    private Double rating;
    private String description;
    private String author;
    private ImageFileType authorImage;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ImageFileType getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(ImageFileType authorImage) {
        this.authorImage = authorImage;
    }
}
