package com.tedi.model;

import com.tedi.dto.RentalSpaceDBType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental_space")
@NamedQueries({
        @NamedQuery(name = "RentalSpace.findByRentalSpaceReference", query = "select r from RentalSpace r where r.rentalSpaceReference = :rentalSpaceReference")
})
@SqlResultSetMapping(
        name = "RentalSpaceDBTypeMapping",
        classes = @ConstructorResult(
                targetClass = RentalSpaceDBType.class,
                columns = {
                        @ColumnResult(name = "binary_identification", type = String.class),
                        @ColumnResult(name = "rent", type = Double.class),
                        @ColumnResult(name = "room_type", type = String.class),
                        @ColumnResult(name = "no_of_beds", type = Integer.class),
                        @ColumnResult(name = "total_reviews", type = Integer.class),
                        @ColumnResult(name = "average_reviews", type = Double.class),
                        @ColumnResult(name = "rental_space_reference", type = String.class)
                }
        )
)
public class RentalSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "room_type", nullable = false, length = 32)
    private String roomType;

    @Column(name = "no_of_bedrooms")
    private Integer noOfBedrooms;

    @Column(name = "max_num_of_people")
    private Integer maxNumOfPeople;

    @Column(name = "no_of_beds")
    private Integer noOfBeds;

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

    @Column(name = "additional_rent_per_person")
    private Double additionalRentPerPerson;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "wirelessInternet", column = @Column(name = "amenities_wireless_internet")),
            @AttributeOverride(name = "cooling", column = @Column(name = "amenities_cooling")),
            @AttributeOverride(name = "heating", column = @Column(name = "amenities_heating")),
            @AttributeOverride(name = "kitchen", column = @Column(name = "amenities_kitchen")),
            @AttributeOverride(name = "television", column = @Column(name = "amenities_television")),
            @AttributeOverride(name = "parkingSpace", column = @Column(name = "amenities_parking_space")),
            @AttributeOverride(name = "elevator", column = @Column(name = "amenities_elevator"))
    })
    private Amenities amenities;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = false)
    @JoinColumn(name = "rental_space_id")
    private List<RentalSpaceDateRange> availableRentPeriods = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "host_id")
    private DiamoniPlusUser host;

    @PositiveOrZero
    @Min(0)
    @Max(5)
    @Digits(integer = 1, fraction = 1)
    @Column(name = "average_reviews", nullable = false)
    private Double averageReviews = 0.0;

    @Column(name = "total_reviews", nullable = false)
    private Integer totalReviews = 0;

    @OneToMany(mappedBy = "rentalSpace", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = false)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = false)
    @JoinColumn(name = "rental_space_id")
    private List<ImageFile> rentalImages = new ArrayList<>();

    @OneToOne(mappedBy = "rentalSpace", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false, orphanRemoval = false)
    private Location location;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = false)
    @JoinColumn(name = "rental_space_id")
    private List<TransportationAccess> transportationAccess = new ArrayList<>();

    @Column(name = "rental_space_reference", nullable = false, unique = true)
    private String rentalSpaceReference;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "diamoni_plus_version")
    private Integer diamoniPlusVersion;

    public List<ImageFile> getRentalImages() {
        return rentalImages;
    }

    public void setRentalImages(List<ImageFile> rentalImages) {
        this.rentalImages = rentalImages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(Integer noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    public Integer getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public void setMaxNumOfPeople(Integer maxNumOfPeople) {
        this.maxNumOfPeople = maxNumOfPeople;
    }

    public Integer getNoOfBeds() {
        return noOfBeds;
    }

    public void setNoOfBeds(Integer noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public Integer getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(Integer noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public Boolean getHasLivingRoom() {
        return hasLivingRoom;
    }

    public void setHasLivingRoom(Boolean hasLivingRoom) {
        this.hasLivingRoom = hasLivingRoom;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Boolean getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(Boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public Boolean getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Boolean getEventsAllowed() {
        return eventsAllowed;
    }

    public void setEventsAllowed(Boolean eventsAllowed) {
        this.eventsAllowed = eventsAllowed;
    }

    public Integer getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getAdditionalRentPerPerson() {
        return additionalRentPerPerson;
    }

    public void setAdditionalRentPerPerson(Double additionalRentPerPerson) {
        this.additionalRentPerPerson = additionalRentPerPerson;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }

    public List<RentalSpaceDateRange> getAvailableRentPeriods() {
        return availableRentPeriods;
    }

    public void setAvailableRentPeriods(List<RentalSpaceDateRange> availableRentPeriods) {
        this.availableRentPeriods = availableRentPeriods;
    }

    public DiamoniPlusUser getHost() {
        return host;
    }

    public void setHost(DiamoniPlusUser host) {
        this.host = host;
    }

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<TransportationAccess> getTransportationAccess() {
        return transportationAccess;
    }

    public void setTransportationAccess(List<TransportationAccess> transportationAccess) {
        this.transportationAccess = transportationAccess;
    }

    public String getRentalSpaceReference() {
        return rentalSpaceReference;
    }

    public void setRentalSpaceReference(String rentalSpaceReference) {
        this.rentalSpaceReference = rentalSpaceReference;
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
