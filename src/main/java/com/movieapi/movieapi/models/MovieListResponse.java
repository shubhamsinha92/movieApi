package com.movieapi.movieapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class MovieListResponse {
  @JsonProperty("page")
  public Integer page;
  @JsonProperty("total_results")
  public Integer totalResults;
  @JsonProperty("total_pages")
  public Integer totalPages;
  @JsonProperty("results")
  public List<Result> results;

  public static class Result {
    @JsonProperty("popularity")
    public Double popularity;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("video")
    public Boolean video;
    @JsonProperty("vote_count")
    public Integer voteCount;
    @JsonProperty("vote_average")
    public Double voteAverage;
    @JsonProperty("title")
    public String title;
    @JsonProperty("release_date")
    public String releaseDate;
    @JsonProperty("original_language")
    public String originalLanguage;
    @JsonProperty("original_title")
    public String originalTitle;
    @JsonProperty("genre_ids")
    public List<Integer> genreIds;
    @JsonProperty("backdrop_path")
    public String backdropPath;
    @JsonProperty("adult")
    public Boolean adult;
    @JsonProperty("overview")
    public String overview;
    @JsonProperty("poster_path")
    public String posterPath;
  }

}
