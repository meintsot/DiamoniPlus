package com.tedi.dao;

import com.tedi.model.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.Optional;

@ApplicationScoped
public class BookingDao {

    @PersistenceContext
    EntityManager em;

    public Optional<Booking> findByBookingReference(String bookingReference) {
        try {
            Query q = em.createNamedQuery("Booking.findByBookingReference", Booking.class)
                    .setParameter("bookingReference", bookingReference);
            return Optional.of((Booking) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
