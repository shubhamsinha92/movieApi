package com.movieapi.movieapi;

import com.movieapi.movieapi.models.CreditsListResponse;
import com.movieapi.movieapi.models.MovieListResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class TestMovieService {

  private MovieApiService movieApiService;

  private Integer pageCount;

  private List<Integer> movieIds;

  private Map<Integer,Integer> castMap;

  @Autowired
  public TestMovieService(MovieApiService movieApiService) {
    this.movieApiService = movieApiService;
    this.pageCount = 1;
    this.movieIds = new ArrayList<>();
    this.castMap = new HashMap<>();
    getMovies(pageCount);
  }

  private void getMovies(Integer pCount){
     movieApiService.movieApi()
        .getMoviesList("606aaffd7ca10f0b80804a1f0674e4e1",
                       "2017-12-01",
                       "2017-12-31",
                       pCount.toString())
        .enqueue(new Callback<MovieListResponse>() {
          @Override
          public void onResponse(Call<MovieListResponse> call,
                                 Response<MovieListResponse> response) {
            if (response.isSuccessful()) {

              int totalPages = response.body().getTotalPages();

              movieIds.addAll(extractMovieIds(response.body().results));

              if(pageCount <= totalPages) {
                getMovies(++pageCount);
              } else {
                createCastMap(movieIds);
              }

            }
          }

          @Override
          public void onFailure(Call<MovieListResponse> call, Throwable throwable) {
            System.out.println(throwable.getMessage());
          }
        });


  }

  private List<Integer> extractMovieIds(List<MovieListResponse.Result> movieResults) {
    return movieResults.stream()
            .map(result -> result.id)
        .collect(Collectors.toList());
  }

  private void createCastMap(List<Integer> movies) {
    movies.forEach(id -> {
      movieApiService.movieApi()
          .getCreditsList(id.toString(),"606aaffd7ca10f0b80804a1f0674e4e1")
          .enqueue(new Callback<CreditsListResponse>() {
            @Override
            public void onResponse(Call<CreditsListResponse> call,
                                   Response<CreditsListResponse> response) {
              if(response.isSuccessful()) {
                response.body().getCast()
                    .forEach(cast -> {
                      Integer castId = cast.getId();
                      if(castMap.containsKey(castId)) {
                        castMap.put(castId,castMap.get(castId) + 1);
                      } else {
                        castMap.put(castId,1);
                      }
                    });
              }
            }
            @Override
            public void onFailure(Call<CreditsListResponse> call, Throwable throwable) {
              System.out.println(throwable.getMessage());
            }
          });
    });
  }

}
