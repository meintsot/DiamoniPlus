package com.tedi.dto;

public class RentalImageType {

    private String data;
    private String name;
    private String mime;
    private Long size;
    private String binaryIdentification;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getBinaryIdentification() {
        return binaryIdentification;
    }

    public void setBinaryIdentification(String binaryIdentification) {
        this.binaryIdentification = binaryIdentification;
    }
}
