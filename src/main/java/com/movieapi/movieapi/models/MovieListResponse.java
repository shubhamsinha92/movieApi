package com.movieapi.movieapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class MovieListResponse {
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
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("video")
    private Boolean video;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("title")
    private String title;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
  }

}
