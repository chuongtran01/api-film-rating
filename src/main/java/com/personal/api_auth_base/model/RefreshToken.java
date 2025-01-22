package com.personal.api_auth_base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "refresh_token")
@Data
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId; // Unique identifier for the record

    @Column(name = "refresh_token", nullable = false, unique = true)
    private String token; // The refresh token string

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user; // Foreign key to associate with the user

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt; // Timestamp when the token was created

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt; // Expiration time for the token

    @Column(name = "revoked", nullable = false)
    private boolean revoked; // Flag to indicate if the token has been revoked

    @Column(name = "ip_address", length = 45)
    private String ipAddress; // Optional: IP address of the user for additional security

    @Column(name = "user_agent")
    private String userAgent; // Optional: User agent details for context

    public RefreshToken(String token, User user, LocalDateTime expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
