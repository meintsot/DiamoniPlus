package com.tedi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Review.findByHost_Username", query = "select r from Review r where r.host.username = :username"),
        @NamedQuery(name = "Review.findByBooking_RentalSpace_RentalSpaceReference", query = "select r from Review r where r.booking.rentalSpace.rentalSpaceReference = :rentalSpaceReference")
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @PositiveOrZero
    @Max(5)
    @Min(0)
    @Digits(integer = 1, fraction = 1)
    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = false)
    private Booking booking;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "diamoni_plus_version")
    private Integer diamoniPlusVersion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private DiamoniPlusUser tenant;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private DiamoniPlusUser host;

    public DiamoniPlusUser getTenant() {
        return tenant;
    }

    public void setTenant(DiamoniPlusUser tenant) {
        this.tenant = tenant;
    }

    public Integer getDiamoniPlusVersion() {
        return diamoniPlusVersion;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiamoniPlusUser getHost() {
        return host;
    }

    public void setHost(DiamoniPlusUser host) {
        this.host = host;
    }
}
