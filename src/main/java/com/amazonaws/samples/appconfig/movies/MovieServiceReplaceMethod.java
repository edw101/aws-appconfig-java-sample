package com.amazonaws.samples.appconfig.movies;

import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class MovieServiceReplaceMethod implements MovieService{
  private final GetConfigurationResponse configurationResponse;
  private final OldMovieService oldMovieService;
  private final NewMovieService newMovieService;

  //App Config properties
  private Boolean boolEnableFeature;
  private int intItemLimit;

  public MovieServiceReplaceMethod(GetConfigurationResponse configurationResponse, OldMovieService oldMovieService, NewMovieService newMovieService) {
    this.configurationResponse = configurationResponse;
    this.oldMovieService = oldMovieService;
    this.newMovieService = newMovieService;
  }

  @Override
  public List<Movie> getMovies() {
    final String appConfigResponse = configurationResponse.content().asUtf8String();

    final JSONObject jsonResponseObject = new JSONObject(appConfigResponse);
    boolEnableFeature = jsonResponseObject.getBoolean("boolEnableFeature");
    intItemLimit = jsonResponseObject.getInt("intItemLimit");

    if(boolEnableFeature){
      return getFirstNElements(newMovieService.getMovies(), intItemLimit);
    }
    return getFirstNElements(oldMovieService.getMovies(), intItemLimit);
  }

  public int getDiscount() {
    final String appConfigResponse = configurationResponse.content().asUtf8String();

    final JSONObject jsonResponseObject = new JSONObject(appConfigResponse);
    boolEnableFeature = jsonResponseObject.getBoolean("boolEnableFeature");
    intItemLimit = jsonResponseObject.getInt("intItemLimit");

    if(boolEnableFeature){
      return newMovieService.getDiscount();
    }
    return oldMovieService.doSomethingElse();
  }

  private List<Movie> getFirstNElements(List<Movie> movies, int limit) {
    int listSize = movies.size();
    int trimmedSize = Math.min(listSize, limit);

    List<Movie> trimmedMovies = new ArrayList<>();
    for (int i= 0; i<trimmedSize; i++) {
      trimmedMovies.add(movies.get(i));
    }

    return trimmedMovies;
  }
}
