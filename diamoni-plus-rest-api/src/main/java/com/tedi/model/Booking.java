package com.tedi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "host_id", nullable = false)
    private DiamoniPlusUser host;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private DiamoniPlusUser tenant;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "rental_space_id", nullable = false)
    private RentalSpace rentalSpace;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "diamoni_plus_version")
    private Integer diamoniPlusVersion;

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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public RentalSpace getRentalSpace() {
        return rentalSpace;
    }

    public void setRentalSpace(RentalSpace rentalSpace) {
        this.rentalSpace = rentalSpace;
    }

    public DiamoniPlusUser getTenant() {
        return tenant;
    }

    public void setTenant(DiamoniPlusUser tenant) {
        this.tenant = tenant;
    }

    public DiamoniPlusUser getHost() {
        return host;
    }

    public void setHost(DiamoniPlusUser host) {
        this.host = host;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
