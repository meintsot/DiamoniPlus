package com.tedi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
public class RentalSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "no_of_bedrooms")
    private Integer noOfBedrooms;

    @Column(name = "no_of_bathrooms")
    private Integer noOfBathrooms;

    @Column(name = "has_living_room")
    private Boolean hasLivingRoom;

    @Column(name = "area", nullable = false)
    private Integer area;

    @Column(name = "smoking_allowed")
    private Boolean smokingAllowed;

    @Column(name = "pets_allowed")
    private Boolean petsAllowed;

    @Column(name = "events_allowed")
    private Boolean eventsAllowed;

    @Column(name = "min_duration")
    private Integer minDuration;

    @Column(name = "rent")
    private Double rent;

    @PositiveOrZero
    @Min(0)
    @Max(5)
    @Digits(integer = 1, fraction = 1)
    @Column(name = "average_reviews", nullable = false)
    private Double averageReviews = 0.0;

    @OneToMany(mappedBy = "rentalSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @OneToOne(mappedBy = "rentalSpace", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Location location;

    @OneToMany(mappedBy = "rentalSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalImage> rentalImages = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "RentalSpace_rentRules",
            joinColumns = @JoinColumn(name = "rentalSpace_id"),
            inverseJoinColumns = @JoinColumn(name = "rentRules_id"))
    private Set<RentRule> rentRules = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "RentalSpace_transportationAccesses",
            joinColumns = @JoinColumn(name = "rentalSpace_id"),
            inverseJoinColumns = @JoinColumn(name = "transportationAccesses_id"))
    private Set<TransportationAccess> transportationAccesses = new LinkedHashSet<>();

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

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Integer getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Boolean getEventsAllowed() {
        return eventsAllowed;
    }

    public void setEventsAllowed(Boolean eventsAllowed) {
        this.eventsAllowed = eventsAllowed;
    }

    public Boolean getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Boolean getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(Boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Boolean getHasLivingRoom() {
        return hasLivingRoom;
    }

    public void setHasLivingRoom(Boolean hasLivingRoom) {
        this.hasLivingRoom = hasLivingRoom;
    }

    public Integer getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(Integer noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public Integer getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(Integer noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<TransportationAccess> getTransportationAccesses() {
        return transportationAccesses;
    }

    public void setTransportationAccesses(Set<TransportationAccess> transportationAccesses) {
        this.transportationAccesses = transportationAccesses;
    }

    public Set<RentRule> getRentRules() {
        return rentRules;
    }

    public void setRentRules(Set<RentRule> rentRules) {
        this.rentRules = rentRules;
    }

    public List<RentalImage> getRentalImages() {
        return rentalImages;
    }

    public void setRentalImages(List<RentalImage> rentalImages) {
        this.rentalImages = rentalImages;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
