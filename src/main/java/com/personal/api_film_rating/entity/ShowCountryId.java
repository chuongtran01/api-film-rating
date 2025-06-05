package com.personal.api_film_rating.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowCountryId implements Serializable {
  private Long show;
  private Integer country;
}