package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.Role;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.repository.RoleRepository;
import com.personal.api_film_rating.repository.UserRepository;
import com.personal.api_film_rating.specifications.UserSpecifications;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Find a user by their ID
     * 
     * @param id
     * @return User
     */
    @Override
    public User findByUserId(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Save a user
     * 
     * @param user
     * @return User
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Find users
     * 
     * @param role
     * @param gender
     * @param displayName
     * @param email
     * @param active
     * @param pageable
     * @return Page<User>
     */
    @Override
    public Page<User> findUsers(List<String> role, List<String> gender, String displayName, String email,
            Boolean active, Pageable pageable) {
        Specification<User> spec = Specification.where(UserSpecifications.hasRoles(role))
                .and(UserSpecifications.hasGenders(gender))
                .and(UserSpecifications.hasEmail(email))
                .and(UserSpecifications.hasDisplayName(displayName))
                .and(UserSpecifications.hasActive(active));
        return userRepository.findAll(spec, pageable);
    }

    /**
     * Inactivate a user by their ID
     * 
     * @param id
     */
    @Override
    @Transactional
    public void inactiveUserById(UUID id) {
        userRepository.inactiveUserById(id);
    }

    /**
     * Find a role by its name
     * 
     * @param name
     * @return Role
     */
    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
