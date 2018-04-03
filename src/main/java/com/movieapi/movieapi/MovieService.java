package com.movieapi.movieapi;

import com.google.common.util.concurrent.RateLimiter;
import com.movieapi.movieapi.models.MovieCreditsListResponse;
import com.movieapi.movieapi.models.MovieListResponse;
import com.movieapi.movieapi.models.TvCreditsListResponse;
import com.movieapi.movieapi.models.TvListResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class MovieService {

  private final RateLimiter rateLimiter = RateLimiter.create(3.0);
  private MovieApiService movieApiService;
  private String apiKey;

  @Autowired
  public MovieService(MovieApiService movieApiService,
                      @Value("${movie.apiKey}") String apiKey) {
    this.movieApiService = movieApiService;
    this.apiKey = apiKey;
  }

  /**
   *
   * @param page given page number
   * @param dateGreaterThan lower range for date
   * @param dateLessThan upper range for date
   * @return total count of pages for given dates
   */
  public Integer getTotalMoviePageCount(Integer page, String dateGreaterThan, String dateLessThan) {
    try {
      return getExecutedResponse(page, dateGreaterThan, dateLessThan)
          .body().getTotalPages();
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }

  private Response<MovieListResponse> getExecutedResponse(Integer page,
                                                          String dateGreaterThan,
                                                          String dateLessThan) throws IOException {
    return movieApiService.movieApi()
        .getMoviesList(apiKey,
                       dateGreaterThan,
                       dateLessThan,
                       page.toString())
        .execute();
  }

  /**
   *
   * @param page given page number
   * @param dateGreaterThan lower range for date
   * @param dateLessThan upper range for date
   * @return total number of pages for TV given dates
   */
  public Integer getTotalTvPageCount(Integer page, String dateGreaterThan, String dateLessThan) {
    try {
      return getExecutedResponseForTV(page, dateGreaterThan, dateLessThan)
          .body().getTotalPages();
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }

  private Response<TvListResponse> getExecutedResponseForTV(Integer page,
                                                            String dateGreaterThan,
                                                            String dateLessThan)
      throws IOException {
    return movieApiService.movieApi()
        .getTvList(apiKey,
                   dateGreaterThan,
                   dateLessThan,
                   page.toString())
        .execute();
  }

  /**
   *
   * @param page given current page
   * @param dateGreaterThan
   * @param dateLessThan
   * @return returns a list of TV ids for given page and dates
   */
  public List<Integer> getTvIds(Integer page, String dateGreaterThan, String dateLessThan) {
    try {
      rateLimiter.acquire();
      Response<TvListResponse> responseForTV = getExecutedResponseForTV(page,
                                                                        dateGreaterThan,
                                                                        dateLessThan);
      if (responseForTV.isSuccessful()) {
        return extractTvIds(responseForTV.body().getResults());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  private List<Integer> extractTvIds(List<TvListResponse.Result> tvResults) {
    return tvResults.stream()
        .map(result -> result.getId())
        .collect(Collectors.toList());
  }

  /**
   *
   * @param page current page
   * @param dateGreaterThan lower bound date
   * @param dateLessThan upper bound date
   * @return a list of movie Ids for the given dates and page
   */
  public List<Integer> getMovieIds(Integer page, String dateGreaterThan, String dateLessThan) {
    try {
      rateLimiter.acquire();
      Response<MovieListResponse> movieListResponseResponse = getExecutedResponse(page,
                                                                                  dateGreaterThan,
                                                                                  dateLessThan);

      if (movieListResponseResponse.isSuccessful()) {
        return extractMovieIds(movieListResponseResponse.body().getResults());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  private List<Integer> extractMovieIds(List<MovieListResponse.Result> movieResults) {
    return movieResults.stream()
        .map(MovieListResponse.Result::getId)
        .collect(Collectors.toList());
  }

  /**
   *
   * @param movies Given list of movie Ids
   * @return returns a set of ids for actors which is obtained by making an API call for each movie Id.
   * This id for actors is found in both movie and tv lists.
   */
  public Set<Integer> createMovieCastSet(List<Integer> movies) {
    Set<Integer> result = new HashSet<>();
    movies
        .forEach(id -> {
          try {
            rateLimiter.acquire();
            Response<MovieCreditsListResponse> creditsListResponse = movieApiService.movieApi()
                .getCreditsListforMovie(id.toString(), apiKey)
                .execute();
            if (creditsListResponse.isSuccessful()) {
              result.addAll(creditsListResponse.body()
                                .getCast()
                                .stream()
                                .map(MovieCreditsListResponse.Cast::getId)
                                .collect(Collectors.toSet()));
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    return result;
  }

  /**
   *
   * @param tvIds Given list of tv Ids
   * @return returns a set of ids for actors which is obtained by making an API call for each tv Id.
   * This unique id for actors is found in both movie and tv lists.
   */
  public Set<Integer> createTvCastSet(List<Integer> tvIds) {
    Set<Integer> result = new HashSet<>();
    tvIds
        .forEach(id -> {
          try {
            rateLimiter.acquire();
            Response<TvCreditsListResponse> creditsListResponse = movieApiService.movieApi()
                .getCreditsListforTv(id.toString(), apiKey)
                .execute();
            if (creditsListResponse.isSuccessful()) {
              result.addAll(creditsListResponse.body()
                                .getCast()
                                .stream()
                                .map(TvCreditsListResponse.Cast::getId)
                                .collect(Collectors.toSet()));
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    return result;
  }

}
