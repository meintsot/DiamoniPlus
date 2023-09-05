package com.tedi.dao;

import com.tedi.dto.AmenitiesType;
import com.tedi.dto.MyRentalSpacesReqMsgType;
import com.tedi.dto.RentalSpaceDBType;
import com.tedi.dto.SearchRentalSpacesReqMsgType;
import com.tedi.model.ImageFile;
import com.tedi.model.RentalSpace;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@ApplicationScoped
public class RentalSpaceDao {

    @PersistenceContext
    EntityManager em;

    public void saveRentalSpace(RentalSpace rentalSpace) {
        em.persist(rentalSpace);
    }

    public void saveRentalImage(ImageFile rentalImage) {
        em.persist(rentalImage);
    }

    public List<RentalSpaceDBType> searchRentalSpaces(SearchRentalSpacesReqMsgType param) {
        return (List<RentalSpaceDBType>) searchRentalSpaces(param, false).getResultList();
    }

    public Long countRentalSpaces(SearchRentalSpacesReqMsgType param) {
        return (Long) searchRentalSpaces(param, true).getSingleResult();
    }

    private Query searchRentalSpaces(SearchRentalSpacesReqMsgType param, boolean isCount) {
        StringBuilder nativeQuery = new StringBuilder();
        List<Consumer<Query>> paramBinders = new ArrayList<>();

        // Constructing the native SQL query
        if (!isCount) {
            nativeQuery.append(
                    "SELECT ri.binary_identification as binary_identification, " +
                    "rs.rent as rent, rs.room_type as room_type, rs.no_of_beds as no_of_beds, " +
                    "rs.total_reviews as total_reviews, rs.average_reviews as average_reviews," +
                    "rs.rental_space_reference as rental_space_reference "
            );
        } else {
            nativeQuery.append("SELECT count(distinct rs.id) ");
        }

        nativeQuery.append("FROM rental_space rs ");
        nativeQuery.append("LEFT JOIN image_file ri ON ri.rental_space_id = rs.id ");
        nativeQuery.append("LEFT JOIN location l ON l.rental_space_id = rs.id ");
        nativeQuery.append("WHERE 1=1 ");

        // Adding optional filters based on provided search criteria
        if (param.getLocation() != null) {
            if (param.getLocation().getCountry() != null) {
                nativeQuery.append("AND l.country LIKE CONCAT('%', :country, '%') ");
                paramBinders.add(query -> query.setParameter("country", param.getLocation().getCountry()));
            }
            if (param.getLocation().getCity() != null) {
                nativeQuery.append("AND l.city LIKE CONCAT('%', :city, '%') ");
                paramBinders.add(query -> query.setParameter("city", param.getLocation().getCity()));
            }
            if (param.getLocation().getAddress() != null) {
                nativeQuery.append("AND l.address LIKE CONCAT('%', :address, '%') ");
                paramBinders.add(query -> query.setParameter("address", param.getLocation().getAddress()));
            }
            if (param.getLocation().getSuburb() != null) {
                nativeQuery.append("AND l.suburb LIKE CONCAT('%', :suburb, '%') ");
                paramBinders.add(query -> query.setParameter("suburb", param.getLocation().getSuburb()));
            }
            if (param.getLocation().getPostcode() != null) {
                nativeQuery.append("AND l.postcode LIKE CONCAT('%', :postcode, '%') ");
                paramBinders.add(query -> query.setParameter("postcode", param.getLocation().getPostcode()));
            }
            if (param.getLocation().getLatitude() != null) {
                nativeQuery.append("AND CONCAT('', l.latitude, '') LIKE CONCAT('%', :latitude, '%') ");
                paramBinders.add(query -> query.setParameter("latitude", param.getLocation().getLatitude()));
            }
            if (param.getLocation().getLongitude() != null) {
                nativeQuery.append("AND CONCAT('', l.longitude, '') LIKE CONCAT('%', :longitude, '%') ");
                paramBinders.add(query -> query.setParameter("longitude", param.getLocation().getLongitude()));
            }
        }

        if (param.getStartDate() != null && param.getEndDate() != null) {
            nativeQuery.append("AND EXISTS (SELECT 1 FROM rental_space_date_range dateRange WHERE dateRange.rental_space_id = rs.id AND dateRange.start_date <= :endDate AND dateRange.end_date >= :startDate AND dateRange.is_available=1) ");
            paramBinders.add(query -> query.setParameter("startDate", param.getStartDate()));
            paramBinders.add(query -> query.setParameter("endDate", param.getEndDate()));
        }

        if (param.getMaxNumOfPeople() != null) {
            nativeQuery.append("AND rs.max_num_of_people >= :maxNumOfPeople ");
            paramBinders.add(query -> query.setParameter("maxNumOfPeople", param.getMaxNumOfPeople()));
        }

        if (param.getRoomType() != null) {
            nativeQuery.append("AND rs.room_type = :roomType ");
            paramBinders.add(query -> query.setParameter("roomType", param.getRoomType().getValue()));
        }

        if (param.getMaximumCost() != null) {
            nativeQuery.append("AND rs.rent <= :maximumCost ");
            paramBinders.add(query -> query.setParameter("maximumCost", param.getMaximumCost()));
        }

        if (param.getAmenities() != null) {
            AmenitiesType amenities = param.getAmenities();

            if (amenities.getWirelessInternet() != null) {
                nativeQuery.append("AND rs.amenities_wireless_internet = :wirelessInternet ");
                paramBinders.add(query -> query.setParameter("wirelessInternet", amenities.getWirelessInternet()));
            }

            if (amenities.getCooling() != null) {
                nativeQuery.append("AND rs.amenities_cooling = :cooling ");
                paramBinders.add(query -> query.setParameter("cooling", amenities.getCooling()));
            }

            if (amenities.getHeating() != null) {
                nativeQuery.append("AND rs.amenities_heating = :heating ");
                paramBinders.add(query -> query.setParameter("heating", amenities.getHeating()));
            }

            if (amenities.getKitchen() != null) {
                nativeQuery.append("AND rs.amenities_kitchen = :kitchen ");
                paramBinders.add(query -> query.setParameter("kitchen", amenities.getKitchen()));
            }

            if (amenities.getTelevision() != null) {
                nativeQuery.append("AND rs.amenities_television = :television ");
                paramBinders.add(query -> query.setParameter("television", amenities.getTelevision()));
            }

            if (amenities.getParkingSpace() != null) {
                nativeQuery.append("AND rs.amenities_parking_space = :parkingSpace ");
                paramBinders.add(query -> query.setParameter("parkingSpace", amenities.getParkingSpace()));
            }

            if (amenities.getElevator() != null) {
                nativeQuery.append("AND rs.amenities_elevator = :elevator ");
                paramBinders.add(query -> query.setParameter("elevator", amenities.getElevator()));
            }
        }

        // Adding the filter for the rental image
        nativeQuery.append("AND ri.id = (SELECT MIN(ri2.id) FROM image_file ri2 WHERE ri2.rental_space_id = rs.id) ");

        nativeQuery.append("order by rs.rent");

        // Set the result class to the native query to map the results to RentalSpaceDBType
        Query query;

        if (!isCount) {
            query = em.createNativeQuery(nativeQuery.toString(), "RentalSpaceDBTypeMapping");
        } else {
            query = em.createNativeQuery(nativeQuery.toString());
        }

        // Bind query parameters using the paramBinders list
        paramBinders.forEach(p -> p.accept(query));

        // Paginate the query results
        int startIndex = (param.getPage() - 1) * param.getPageSize();
        query.setFirstResult(startIndex);
        query.setMaxResults(param.getPageSize());

        return query;
    }

    public Optional<RentalSpace> retrieveRentalSpaceDetails(String rentalSpaceReference) {
        try {
            Query q = em.createNamedQuery("RentalSpace.findByRentalSpaceReferenceWithAvailableRentPeriods", RentalSpace.class)
                    .setParameter("rentalSpaceReference", rentalSpaceReference);
            return Optional.of((RentalSpace) q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<ImageFile> retrieveRentalImage(String rentalSpaceReference, String binaryIdentification) {
        try {
            TypedQuery<ImageFile> query = em.createQuery(
                            "select ri from RentalSpace r join r.rentalImages ri " +
                                    "where r.rentalSpaceReference = :rentalSpaceReference " +
                                    "and ri.binaryIdentification = :binaryIdentification",
                            ImageFile.class)
                    .setParameter("rentalSpaceReference", rentalSpaceReference)
                    .setParameter("binaryIdentification", binaryIdentification);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<ImageFile> retrieveRentalImages(List<String> binaryIdentifications) {
        return em.createQuery(
                "select ri from ImageFile ri " +
                        "where ri.binaryIdentification in :binaryIdentifications ", ImageFile.class)
                .setParameter("binaryIdentifications", binaryIdentifications)
                .getResultList();
    }

    public boolean deleteRentalImage(String binaryIdentification) {
        int rowsDeleted = em.createQuery("delete from ImageFile ri where ri.binaryIdentification = :binaryIdentification")
                .setParameter("binaryIdentification", binaryIdentification)
                .executeUpdate();

        return rowsDeleted > 0;
    }

    public Query myRentalSpaces(MyRentalSpacesReqMsgType param, String user, boolean isCount) {
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

        nativeQuery.append("LEFT JOIN diamoni_plus_user u ON u.id = rs.host_id ");
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


    public List<RentalSpaceDBType> myRentalSpaces(MyRentalSpacesReqMsgType param, String user) {
        return (List<RentalSpaceDBType>) myRentalSpaces(param, user, false).getResultList();
    }

    public Long countMyRentalSpaces(MyRentalSpacesReqMsgType param, String user) {
        return (Long) myRentalSpaces(param, user, true).getSingleResult();
    }
}
