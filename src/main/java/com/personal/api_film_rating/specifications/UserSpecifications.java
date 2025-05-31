package com.personal.api_film_rating.specifications;

import com.personal.api_film_rating.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpecifications {
    /**
     * Has roles
     * 
     * @param roles
     * @return Specification<User>
     */
    public static Specification<User> hasRoles(List<String> roles) {
        return (root, query, cb) -> {
            if (roles == null || roles.isEmpty()) {
                return cb.conjunction(); // no filter
            }
            CriteriaBuilder.In<String> inClause = cb.in(cb.lower(root.get("role").get("name")));
            for (String role : roles) {
                inClause.value(role.toLowerCase());
            }
            return inClause;
        };
    }

    /**
     * Has genders
     * 
     * @param genders
     * @return Specification<User>
     */
    public static Specification<User> hasGenders(List<String> genders) {
        return (root, query, cb) -> {
            if (genders == null || genders.isEmpty()) {
                return cb.conjunction(); // no filter
            }
            CriteriaBuilder.In<String> inClause = cb.in(cb.lower(root.get("gender")));
            for (String gender : genders) {
                inClause.value(gender.toLowerCase());
            }
            return inClause;
        };
    }

    /**
     * Has email
     * 
     * @param email
     * @return Specification<User>
     */
    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null)
                return null;
            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    /**
     * Has display name
     * 
     * @param displayName
     * @return Specification<User>
     */
    public static Specification<User> hasDisplayName(String displayName) {
        return (root, query, cb) -> {
            if (displayName == null)
                return null;
            return cb.like(cb.lower(root.get("displayName")), "%" + displayName.toLowerCase() + "%");
        };
    }

    /**
     * Has active
     * 
     * @param active
     * @return Specification<User>
     */
    public static Specification<User> hasActive(Boolean active) {
        return (root, query, cb) -> {
            if (active == null)
                return null;
            return cb.equal(root.get("active"), active);
        };
    }
}
