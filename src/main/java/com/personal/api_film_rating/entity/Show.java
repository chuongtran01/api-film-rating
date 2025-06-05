package com.personal.api_film_rating.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shows")
public class Show {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "show_type_id", nullable = false)
  private ShowType showType;

  private Integer duration; // in minutes

  private String poster; // URL to poster image

  private String trailer; // URL to trailer video

  @Column(precision = 3, scale = 1)
  private BigDecimal rating; // Average rating (0.0 to 10.0)

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_id", nullable = false)
  private ShowStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id", nullable = false)
  private Language language;

  @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
  @Builder.Default
  private List<ShowGenre> genres = new ArrayList<>();

  @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
  @Builder.Default
  private List<ShowStreamingPlatform> streamingPlatforms = new ArrayList<>();

  @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
  @Builder.Default
  private List<ShowCountry> countries = new ArrayList<>();

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}