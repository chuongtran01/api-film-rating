package com.personal.api_film_rating.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "show_countries")
@IdClass(ShowCountryId.class)
public class ShowCountry {
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "show_id")
  private Show show;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id")
  private Country country;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
}