package com.tedi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.time.LocalDateTime;

@Entity
@Table(name = "transportation_access")
public class TransportationAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "transportation_name", nullable = false, length = 64)
    private String transportationName;

    @Column(name = "transportation_type", nullable = false, length = 32)
    private String transportationType;

    @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be between -90 and 90 degrees.")
    @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be between -90 and 90 degrees.")
    @Digits(integer = 2, fraction = 6, message = "Latitude must have at most 2 integer digits and 6 fraction digits.")
    @Column(name = "latitude")
    private Double latitude;

    @DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be between -180 and 180 degrees.")
    @DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be between -180 and 180 degrees.")
    @Digits(integer = 3, fraction = 6, message = "Longitude must have at most 3 integer digits and 6 fraction digits.")
    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "diamoni_plus_version")
    private Integer diamoniPlusVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransportationName() {
        return transportationName;
    }

    public void setTransportationName(String transportationName) {
        this.transportationName = transportationName;
    }

    public String getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getDiamoniPlusVersion() {
        return diamoniPlusVersion;
    }

    public void setDiamoniPlusVersion(Integer diamoniPlusVersion) {
        this.diamoniPlusVersion = diamoniPlusVersion;
    }
}
