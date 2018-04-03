package com.movieapi.movieapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class TvCreditsListResponse {

  @JsonProperty("cast")
  private List<Cast> cast;
  @JsonProperty("crew")
  private List<Crew> crew;
  @JsonProperty("id")
  private Integer id;

  @Data
  public static class Cast {
    @JsonProperty("character")
    private String character;
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("gender")
    private Integer gender;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("order")
    private Integer order;
    @JsonProperty("profile_path")
    private String profilePath;
  }

  @Data
  public static class Crew {
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("department")
    private String department;
    @JsonProperty("gender")
    private Integer gender;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("job")
    private String job;
    @JsonProperty("name")
    private String name;
    @JsonProperty("profile_path")
    private String profilePath;
  }


}
