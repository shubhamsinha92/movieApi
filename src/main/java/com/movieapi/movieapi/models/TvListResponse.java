package com.movieapi.movieapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class TvListResponse {
  @JsonProperty("page")
  private Integer page;
  @JsonProperty("total_results")
  private Integer totalResults;
  @JsonProperty("total_pages")
  private Integer totalPages;
  @JsonProperty("results")
  private List<Result> results;

  @Data
  public static class Result {
    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    @JsonProperty("name")
    private String name;
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("origin_country")
    private List<String> originCountry;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("vote_average")
    private Integer voteAverage;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
  }

}
