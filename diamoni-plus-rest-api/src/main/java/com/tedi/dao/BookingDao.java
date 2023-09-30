package com.tedi.dao;

import com.tedi.dto.MyBookingsReqMsgType;
import com.tedi.dto.RentalSpaceDBType;
import com.tedi.model.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookingDao {

    @PersistenceContext
    EntityManager em;

    public void saveBooking(Booking booking) {
        em.persist(booking);
    }

    public Optional<Booking> findByBookingReference(String bookingReference) {
        try {
            Query q = em.createNamedQuery("Booking.findByBookingReference", Booking.class)
                    .setParameter("bookingReference", bookingReference);
            return Optional.of((Booking) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Booking> findByTenantUsernameAndRentalSpaceReference(String username, String rentalSpaceReference) {
        try {
            Query q = em.createNamedQuery("Booking.findByTenant_UsernameAndRentalSpace_RentalSpaceReference", Booking.class)
                    .setParameter("username", username)
                    .setParameter("rentalSpaceReference", rentalSpaceReference);
            return Optional.of((Booking) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void cancelBooking(Booking booking) {
        em.createNamedQuery("Booking.deleteById").setParameter("id", booking.getId()).executeUpdate();
    }

    public List<RentalSpaceDBType> myBookings(MyBookingsReqMsgType param, String user) {
        return (List<RentalSpaceDBType>) myBookings(param, user, false).getResultList();
    }

    public Long countMyBookings(MyBookingsReqMsgType param, String user) {
        return (Long) myBookings(param, user, true).getSingleResult();
    }

    public Query myBookings(MyBookingsReqMsgType param, String user, boolean isCount) {
        StringBuilder nativeQuery = new StringBuilder();

        // Constructing the native SQL query
        if (!isCount) {
            nativeQuery.append(
                    "SELECT rs.rental_space_reference as rental_space_reference, " +
                            "MIN(ri.binary_identification) as binary_identification, " +
                            "rs.rent as rent, rs.room_type as room_type, rs.no_of_beds as no_of_beds, " +
                            "rs.total_reviews as total_reviews, rs.average_reviews as average_reviews FROM rental_space rs "
            );
            nativeQuery.append("LEFT JOIN image_file ri ON ri.rental_space_id = rs.id ");
            nativeQuery.append("LEFT JOIN location l ON l.rental_space_id = rs.id ");
        } else {
            nativeQuery.append("SELECT COUNT(distinct rs.id) FROM rental_space rs ");
        }

        nativeQuery.append("LEFT JOIN booking b ON b.rental_space_id = rs.id ");
        nativeQuery.append("LEFT JOIN diamoni_plus_user u ON u.id = b.tenant_id ");
        nativeQuery.append("WHERE u.username = :user ");

        // Adjusted filter for the rental image using a subquery
        if (!isCount) {
            nativeQuery.append("AND ri.id = (SELECT MIN(ri2.id) FROM image_file ri2 WHERE ri2.id = ri.id) ");
            nativeQuery.append("GROUP BY rs.rental_space_reference, rs.rent, rs.room_type, rs.no_of_beds, rs.total_reviews, rs.average_reviews ");
            nativeQuery.append("ORDER BY rs.rent");
        }

        // Create the native SQL query
        Query query;

        if (!isCount) {
            query = em.createNativeQuery(nativeQuery.toString(), "RentalSpaceDBTypeMapping");
        } else {
            query = em.createNativeQuery(nativeQuery.toString());
        }

        // Paginate the query results
        int startIndex = (param.getPage() - 1) * param.getPageSize();
        query.setParameter("user", user);
        query.setFirstResult(startIndex);
        query.setMaxResults(param.getPageSize());

        return query;
    }
}
