package com.tedi.dto;

public class RetrieveReviewsReqMsgType {

    private Integer page;
    private Integer pageSize;
    private String username;
    private String rentalSpaceReference;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRentalSpaceReference() {
        return rentalSpaceReference;
    }

    public void setRentalSpaceReference(String rentalSpaceReference) {
        this.rentalSpaceReference = rentalSpaceReference;
    }
}
