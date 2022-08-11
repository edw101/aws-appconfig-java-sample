package com.amazonaws.samples.appconfig.movies;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class NewMovieService implements MovieService{
  private static final Movie[] NEW_MOVIES = {
          new Movie(1L, "Jack Sparrow"),
          new Movie(2L, "ZooTopia"),
          new Movie(3L, "Frozen"),
          new Movie(4L, "Home Together"),
          new Movie(5L, "Charcoal Black"),
          new Movie(6L, "Mulan"),
          new Movie(7L, "Antman"),
          new Movie(8L, "Avenger"),
          new Movie(9L, "Doctor Strange"),
          new Movie(10L, "Dr. Stone")
  };

  @Override
  public List<Movie> getMovies() {
    List<Movie> movies = new ArrayList<>();
    Collections.addAll(movies, NEW_MOVIES);
    return movies;
  }

  public int getDiscount() {
    return 10;
  }
}
