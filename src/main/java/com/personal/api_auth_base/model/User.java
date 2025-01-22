package com.personal.api_auth_base.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; // Primary key for the user

    @Column(name = "user_name", nullable = false, unique = true, length = 150)
    private String username; // Unique username

    @Column(name = "password", nullable = false)
    private String password; // Hashed password

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName; // User's first name

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName; // User's last name

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Timestamp when the user was created

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // Timestamp when the user was last updated

    @Column(name = "active", nullable = false)
    private boolean active; // User's status (active/inactive)

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_role", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key in user_role for User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key in user_role for Role
    )
    private Set<Role> roles = new HashSet<>();

    public Collection<GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
