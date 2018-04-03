package com.movieapi.movieapi.api;

import com.movieapi.movieapi.models.MovieCreditsListResponse;
import com.movieapi.movieapi.models.MovieListResponse;
import com.movieapi.movieapi.models.TvCreditsListResponse;
import com.movieapi.movieapi.models.TvListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
  @GET("/3/discover/movie")
  @Headers({"Accept: application/json", "Content-Type: application/json"})
  Call<MovieListResponse> getMoviesList(@Query("api_key") String apiKey,
                                        @Query("primary_release_date.gte")
                                            String primaryReleaseDateGreaterThan,
                                        @Query("primary_release_date.lte")
                                            String primaryReleaseDateLessThan,
                                        @Query("page") String page
  );

  @GET("/3/movie/{movieId}/credits")
  @Headers({"Accept: application/json", "Content-Type: application/json"})
  Call<MovieCreditsListResponse> getCreditsListforMovie(@Path(value = "movieId") String movieId,
                                                        @Query("api_key") String apiKey);

  @GET("/3/tv/{tvId}/credits")
  @Headers({"Accept: application/json", "Content-Type: application/json"})
  Call<TvCreditsListResponse> getCreditsListforTv(@Path(value = "tvId") String tvId,
                                                  @Query("api_key") String apiKey);

  @GET("/3/discover/tv")
  @Headers({"Accept: application/json", "Content-Type: application/json"})
  Call<TvListResponse> getTvList(@Query("api_key") String apiKey,
                                 @Query("first_air_date.gte")
                                            String firstAirDateGreaterThan,
                                 @Query("first_air_date.lte")
                                            String firstAirDateLessThan,
                                 @Query("page") String page
  );

}
