package com.tedi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Booking.findByBookingReference", query = "select b from Booking b where b.bookingReference = :bookingReference"),
        @NamedQuery(name = "Booking.findByTenant_UsernameAndRentalSpace_RentalSpaceReference", query = "select b from Booking b where b.tenant.username = :username and b.rentalSpace.rentalSpaceReference = :rentalSpaceReference"),
        @NamedQuery(name = "Booking.deleteById", query = "delete from Booking b where b.id = :id")
})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "booking_reference", nullable = false)
    private String bookingReference;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "host_id", nullable = false)
    private DiamoniPlusUser host;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private DiamoniPlusUser tenant;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "rental_space_id", nullable = false)
    private RentalSpace rentalSpace;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false, orphanRemoval = false)
    @JoinColumn(name = "booking_date_range_id", nullable = false)
    private RentalSpaceDateRange bookingDateRange;

    @OneToOne(mappedBy = "booking", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = false)
    private Review review;

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

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public DiamoniPlusUser getHost() {
        return host;
    }

    public void setHost(DiamoniPlusUser host) {
        this.host = host;
    }

    public DiamoniPlusUser getTenant() {
        return tenant;
    }

    public void setTenant(DiamoniPlusUser tenant) {
        this.tenant = tenant;
    }

    public RentalSpace getRentalSpace() {
        return rentalSpace;
    }

    public void setRentalSpace(RentalSpace rentalSpace) {
        this.rentalSpace = rentalSpace;
    }

    public RentalSpaceDateRange getBookingDateRange() {
        return bookingDateRange;
    }

    public void setBookingDateRange(RentalSpaceDateRange bookingDateRange) {
        this.bookingDateRange = bookingDateRange;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
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
