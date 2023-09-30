package com.tedi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Message.findByDiscussion_DiscussionReference", query = "select m from Message m " +
                "where m.discussion.discussionReference = :discussionReference order by m.createdAt desc")
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private DiamoniPlusUser sender;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private DiamoniPlusUser receiver;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

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

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public DiamoniPlusUser getSender() {
        return sender;
    }

    public void setSender(DiamoniPlusUser sender) {
        this.sender = sender;
    }

    public DiamoniPlusUser getReceiver() {
        return receiver;
    }

    public void setReceiver(DiamoniPlusUser receiver) {
        this.receiver = receiver;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
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
