package com.tedi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.time.LocalDateTime;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "country", nullable = false, length = 45)
    private String country;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "suburb", nullable = false, length = 45)
    private String suburb;

    @Column(name = "postcode", length = 10)
    private String postcode;

    @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be between -90 and 90 degrees.")
    @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be between -90 and 90 degrees.")
    @Digits(integer = 2, fraction = 6, message = "Latitude must have at most 2 integer digits and 6 fraction digits.")
    @Column(name = "latitude")
    private double latitude;

    @DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be between -180 and 180 degrees.")
    @DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be between -180 and 180 degrees.")
    @Digits(integer = 3, fraction = 6, message = "Longitude must have at most 3 integer digits and 6 fraction digits.")
    @Column(name = "longitude")
    private double longitude;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false, orphanRemoval = false)
    @JoinColumn(name = "rental_space_id", nullable = false)
    private RentalSpace rentalSpace;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public RentalSpace getRentalSpace() {
        return rentalSpace;
    }

    public void setRentalSpace(RentalSpace rentalSpace) {
        this.rentalSpace = rentalSpace;
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
