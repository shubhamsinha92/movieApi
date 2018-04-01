package com.movieapi.movieapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapi.movieapi.api.MovieApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MovieApiService {

  private MovieApi movieApi;

  public MovieApiService(String apiUrl,
                         String apiKey,
                         ObjectMapper objectMapper) {
    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    JacksonConverterFactory converterFactory = JacksonConverterFactory.create(objectMapper);

    Retrofit retrofit = new Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(apiUrl)
        .addConverterFactory(converterFactory)
        .build();

    this.movieApi = retrofit.create(MovieApi.class);

  }

  public MovieApi movieApi() {
    return movieApi;
  }

}
