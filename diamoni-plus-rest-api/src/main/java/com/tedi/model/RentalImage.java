package com.tedi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class RentalImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mime", nullable = false, length = 50)
    private String mime;

    @Column(name = "size", nullable = false)
    private Long size;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "rental_space_id", nullable = false)
    private RentalSpace rentalSpace;

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RentalSpace getRentalSpace() {
        return rentalSpace;
    }

    public void setRentalSpace(RentalSpace rentalSpace) {
        this.rentalSpace = rentalSpace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
