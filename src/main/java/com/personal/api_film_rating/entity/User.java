package com.personal.api_film_rating.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.personal.api_film_rating.enums.EnumGender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "display_name", nullable = false, unique = true, length = 150)
    private String displayName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String password;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Role role;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private EnumGender gender;

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UuidCreator.getTimeOrdered();
        }
    }
}
