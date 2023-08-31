package com.tedi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diamoni_plus_user")
@NamedQueries({
        @NamedQuery(name = "DiamoniPlusUser.findByUsername", query = "select d from DiamoniPlusUser d where d.username = :username"),
        @NamedQuery(name = "DiamoniPlusUser.findByEmail", query = "select d from DiamoniPlusUser d where d.email = :email")
})
public class DiamoniPlusUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, length = 50, nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> hostedBookings = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> tenantedBookings = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewsReceived = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewsSent = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "diamoni_plus_version")
    private Integer diamoniPlusVersion;

    @Column(name = "phone", length = 32)
    private String phone;

    @Lob
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_host_approved")
    private Boolean isHostApproved = false;

    @PositiveOrZero
    @Min(0)
    @Max(5)
    @Digits(integer = 1, fraction = 1)
    @Column(name = "average_reviews", nullable = false)
    private Double averageReviews = 0.0;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalSpace> rentalSpaces = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Booking> getHostedBookings() {
        return hostedBookings;
    }

    public void setHostedBookings(List<Booking> hostedBookings) {
        this.hostedBookings = hostedBookings;
    }

    public List<Booking> getTenantedBookings() {
        return tenantedBookings;
    }

    public void setTenantedBookings(List<Booking> tenantedBookings) {
        this.tenantedBookings = tenantedBookings;
    }

    public List<Review> getReviewsReceived() {
        return reviewsReceived;
    }

    public void setReviewsReceived(List<Review> reviewsReceived) {
        this.reviewsReceived = reviewsReceived;
    }

    public List<Review> getReviewsSent() {
        return reviewsSent;
    }

    public void setReviewsSent(List<Review> reviewsSent) {
        this.reviewsSent = reviewsSent;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getHostApproved() {
        return isHostApproved;
    }

    public void setHostApproved(Boolean hostApproved) {
        isHostApproved = hostApproved;
    }

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }

    public List<RentalSpace> getRentalSpaces() {
        return rentalSpaces;
    }

    public void setRentalSpaces(List<RentalSpace> rentalSpaces) {
        this.rentalSpaces = rentalSpaces;
    }
}
