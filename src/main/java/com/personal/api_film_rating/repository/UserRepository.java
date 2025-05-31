package com.personal.api_film_rating.repository;

import com.personal.api_film_rating.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.id = :id")
    void inactiveUserById(UUID id);
}
