package com.amazonaws.samples.appconfig.movies;

import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

public interface AppConfigService {

  public GetConfigurationResponse getConfigurationResponse();

  public Boolean isEnableFeature();
}
