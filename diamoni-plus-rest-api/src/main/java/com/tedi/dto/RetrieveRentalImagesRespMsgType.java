package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class RetrieveRentalImagesRespMsgType {

    List<RentalImageType> rentalImages = new ArrayList<>();
    Integer totalResults;

    public List<RentalImageType> getRentalImages() {
        return rentalImages;
    }

    public void setRentalImages(List<RentalImageType> rentalImages) {
        this.rentalImages = rentalImages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
