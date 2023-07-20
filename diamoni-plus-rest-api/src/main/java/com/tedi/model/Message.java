package com.tedi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private DiamoniPlusUser sender;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private DiamoniPlusUser receiver;

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

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public DiamoniPlusUser getReceiver() {
        return receiver;
    }

    public void setReceiver(DiamoniPlusUser receiver) {
        this.receiver = receiver;
    }

    public DiamoniPlusUser getSender() {
        return sender;
    }

    public void setSender(DiamoniPlusUser sender) {
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
