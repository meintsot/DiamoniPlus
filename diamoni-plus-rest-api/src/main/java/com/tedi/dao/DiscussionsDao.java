package com.tedi.dao;

import com.tedi.dto.CreateAndRetrieveDiscussionReqMsgType;
import com.tedi.dto.DiscussionType;
import com.tedi.dto.RetrieveMessagesReqMsgType;
import com.tedi.model.Discussion;
import com.tedi.model.Message;
import com.tedi.model.RoleType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DiscussionsDao {

    @PersistenceContext
    EntityManager em;

    public Optional<Discussion> findByHostUsernameAndTenantUsername(
            CreateAndRetrieveDiscussionReqMsgType param,
            String tenantUsername
    ) {
        try {
            Query q = em.createNamedQuery("Discussion.findByHost_UsernameAndTenant_Username", Discussion.class)
                    .setParameter("hostUsername", param.getHostUsername())
                    .setParameter("tenantUsername", tenantUsername);
            return Optional.of((Discussion) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Discussion> findByDiscussionReference(String discussionReference) {
        try {
            Query q = em.createNamedQuery("Discussion.findByDiscussionReference", Discussion.class)
                    .setParameter("discussionReference", discussionReference);
            return Optional.of((Discussion) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void saveDiscussion(Discussion discussion) {
        em.persist(discussion);
    }

    public List<Message> retrieveMessages(RetrieveMessagesReqMsgType param) {

        int startIndex = (param.getPage() - 1) * param.getPageSize();

        return em.createNamedQuery("Message.findByDiscussion_DiscussionReference", Message.class)
                .setParameter("discussionReference", param.getDiscussionReference())
                .setFirstResult(startIndex)
                .setMaxResults(param.getPageSize())
                .getResultList();
    }

    public List<DiscussionType> retrieveDiscussions(String username, String role) {

        if (RoleType.HOST.getValue().equals(role)) {
            return em.createQuery("select new com.tedi.dto.DiscussionType(" +
                            "d.discussionReference, d.tenant.username, d.tenant.avatar.binaryIdentification" +
                            ") from Discussion d " +
                            "where d.host.username = :username order by d.updatedAt desc", DiscussionType.class)
                    .setParameter("username", username)
                    .getResultList();
        } else {
            return em.createQuery("select new com.tedi.dto.DiscussionType(" +
                            "d.discussionReference, d.host.username, d.host.avatar.binaryIdentification" +
                            ") from Discussion d " +
                            "where d.tenant.username = :username order by d.updatedAt desc", DiscussionType.class)
                    .setParameter("username", username)
                    .getResultList();
        }
    }

    public void saveMessage(Message message) {
        em.persist(message);
    }
}
