package com.tedi.dao;

import com.tedi.model.DiamoniPlusUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DiamoniPlusUserDao {

    @PersistenceContext
    EntityManager em;

    public Optional<DiamoniPlusUser> findByUsername(String username) {
        try {
            Query q = em.createNamedQuery("DiamoniPlusUser.findByUsername", DiamoniPlusUser.class)
                    .setParameter("username", username);
            return Optional.of((DiamoniPlusUser) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<DiamoniPlusUser> findByEmail(String email) {
        try {
            Query q = em.createNamedQuery("DiamoniPlusUser.findByEmail", DiamoniPlusUser.class)
                    .setParameter("email", email);
            return Optional.of((DiamoniPlusUser) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void saveDiamoniPlusUser(DiamoniPlusUser diamoniPlusUser) {
        em.persist(diamoniPlusUser);
    }
}
