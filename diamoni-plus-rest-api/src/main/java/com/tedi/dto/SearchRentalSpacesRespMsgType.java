package com.tedi.dto;

import java.util.List;

public class SearchRentalSpacesRespMsgType {

    private List<RentalSpaceResultType> rentalSpaceResults;
    private Long totalResults;

    public List<RentalSpaceResultType> getRentalSpaceResults() {
        return rentalSpaceResults;
    }

    public void setRentalSpaceResults(List<RentalSpaceResultType> rentalSpaceResults) {
        this.rentalSpaceResults = rentalSpaceResults;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }
}
