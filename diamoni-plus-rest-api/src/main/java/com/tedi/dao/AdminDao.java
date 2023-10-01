package com.tedi.dao;

import com.tedi.dto.*;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.model.RoleType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ApplicationScoped
public class AdminDao {

    @PersistenceContext
    EntityManager em;

    public List<UserProfileResult> searchUserProfiles(SearchUserProfilesReqMsgType param) {
        return (List<UserProfileResult>) searchUserProfiles(param, false).getResultList();
    }

    public Long countUserProfiles(SearchUserProfilesReqMsgType param) {
        return (Long) searchUserProfiles(param, true).getSingleResult();
    }

    private Query searchUserProfiles(SearchUserProfilesReqMsgType param, boolean isCount) {
        StringBuilder jpql = new StringBuilder();
        List<Consumer<Query>> paramBinders = new ArrayList<>();

        if (!isCount) {
            jpql.append(
                    "select new com.tedi.dto.UserProfileResult(" +
                            "u.username, u.email, u.firstName, u.lastName, u.phone, u.roleType, " +
                            "u.isHostApproved, u.averageReviews" +
                            ") "
            );
        } else {
            jpql.append("SELECT count(distinct u.id) ");
        }

        jpql.append("from DiamoniPlusUser u where 1=1 ");

        if (param.getUsername() != null) {
            jpql.append("and u.username like concat('%', :username, '%') ");
            paramBinders.add(query -> query.setParameter("username", param.getUsername()));
        }

        if (param.getEmail() != null) {
            jpql.append("and u.email like concat('%', :email, '%') ");
            paramBinders.add(query -> query.setParameter("email", param.getEmail()));
        }

        if (param.getFirstName() != null) {
            jpql.append("and u.firstName like concat('%', :firstName, '%') ");
            paramBinders.add(query -> query.setParameter("firstName", param.getFirstName()));
        }

        if (param.getLastName() != null) {
            jpql.append("and u.lastName like concat('%', :lastName, '%') ");
            paramBinders.add(query -> query.setParameter("lastName", param.getLastName()));
        }

        if (param.getPhone() != null) {
            jpql.append("and u.phone like concat('%', :phone, '%') ");
            paramBinders.add(query -> query.setParameter("phone", param.getPhone()));
        }

        if (param.getRoleType() != null) {
            jpql.append("and u.roleType = :roleType ");
            paramBinders.add(query -> query.setParameter("roleType", RoleType.fromValue(param.getRoleType())));
        }

        if (param.getIsHostApproved() != null) {
            jpql.append("and u.isHostApproved = :isHostApproved ");
            paramBinders.add(query -> query.setParameter("isHostApproved", param.getIsHostApproved()));
        }

        if (param.getAverageReviews() != null) {
            jpql.append("and abs(u.averageReviews - :averageReviews) < 0.1 ");
            paramBinders.add(query -> query.setParameter("averageReviews", param.getAverageReviews()));
        }

        Query query;

        if (!isCount) {
            query = em.createQuery(jpql.toString(), UserProfileResult.class);
        } else {
            query = em.createQuery(jpql.toString(), Long.class);
        }

        if (!isCount) {
            int startIndex = (param.getPage() - 1) * param.getPageSize();
            query.setFirstResult(startIndex);
            query.setMaxResults(param.getPageSize());
        }

        paramBinders.forEach(p -> p.accept(query));

        return query;
    }

    public List<DiamoniPlusUser> findAll() {
        return em.createNamedQuery("DiamoniPlusUser.findAll", DiamoniPlusUser.class).getResultList();
    }
}
