package com.amazonaws.samples.appconfig.movies;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class OldMovieService implements MovieService{
  private static final Movie[] OLD_MOVIES = {
          new Movie(1L, "Titanic"),
          new Movie(2L, "Lion King"),
          new Movie(3L, "Cinderella"),
          new Movie(4L, "Home Alone"),
          new Movie(5L, "Snow White"),
          new Movie(6L, "Tarzan"),
          new Movie(7L, "Spiderman"),
          new Movie(8L, "Old Movie 8"),
          new Movie(9L, "Old Movie 9"),
          new Movie(10L, "New Movie 10")
  };

  @Override
  public List<Movie> getMovies() {
    List<Movie> movies = new ArrayList<>();
    Collections.addAll(movies, OLD_MOVIES);
    return movies;
  }

  public int doSomethingElse() {
    return 100;
  }
}
