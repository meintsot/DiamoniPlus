package com.tedi.dto;

public class ImageFileType {

    private String binaryIdentification;
    private String data;
    private String filename;
    private String mime;

    public String getBinaryIdentification() {
        return binaryIdentification;
    }

    public void setBinaryIdentification(String binaryIdentification) {
        this.binaryIdentification = binaryIdentification;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
