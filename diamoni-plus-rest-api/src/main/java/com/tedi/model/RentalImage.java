package com.tedi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental_image")
@NamedQueries({
        @NamedQuery(name = "RentalImage.findByBinaryIdentificationIn", query = "select r from RentalImage r where r.binaryIdentification in :binaryIdentifications")
})
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

    @Column(name = "binary_identification", nullable = false)
    private String binaryIdentification;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getBinaryIdentification() {
        return binaryIdentification;
    }

    public void setBinaryIdentification(String binaryIdentification) {
        this.binaryIdentification = binaryIdentification;
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
