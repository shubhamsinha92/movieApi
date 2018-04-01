package com.movieapi.movieapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CreditsListResponse {
  @JsonProperty("id")
  public Integer id;
  @JsonProperty("cast")
  public List<Cast> cast;
  @JsonProperty("crew")
  public List<Crew> crew;

  @Data
  public static class Cast {
    @JsonProperty("cast_id")
    public Integer castId;
    @JsonProperty("character")
    public String character;
    @JsonProperty("credit_id")
    public String creditId;
    @JsonProperty("gender")
    public Integer gender;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("order")
    public Integer order;
    @JsonProperty("profile_path")
    public String profilePath;
  }

  @Data
  public static class Crew {
    @JsonProperty("credit_id")
    public String creditId;
    @JsonProperty("department")
    public String department;
    @JsonProperty("gender")
    public Integer gender;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("job")
    public String job;
    @JsonProperty("name")
    public String name;
    @JsonProperty("profile_path")
    public Object profilePath;
  }
}
