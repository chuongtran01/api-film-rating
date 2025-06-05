package com.personal.api_film_rating.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowStreamingPlatformId implements Serializable {
  private Long show;
  private Integer platform;
}