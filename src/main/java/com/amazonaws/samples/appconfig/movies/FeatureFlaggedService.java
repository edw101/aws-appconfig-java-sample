package com.amazonaws.samples.appconfig.movies;

import org.springframework.stereotype.Component;

@Component
class FeatureFlaggedService extends FeatureFlagFactoryBean<MovieService> {

    public FeatureFlaggedService(AppConfigService appConfigService) {
        super(
                MovieService.class,
                appConfigService::isEnableFeature,
                new NewMovieService(),
                new OldMovieService());
    }
}
