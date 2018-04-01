package com.movieapi.movieapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapi.movieapi.MovieApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieApiConfig {
  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
  @Bean
  public MovieApiService movieApiService(ObjectMapper objectMapper) {
    return new MovieApiService("https://api.themoviedb.org/",
                               "606aaffd7ca10f0b80804a1f0674e4e1",
                               objectMapper);
  }
}
