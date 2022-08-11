package com.amazonaws.samples.appconfig.movies;

import com.amazonaws.samples.appconfig.AppConfigUtility;
import com.amazonaws.samples.appconfig.cache.ConfigurationCache;
import com.amazonaws.samples.appconfig.model.ConfigurationKey;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class AppConfigServiceImpl implements AppConfigService{
  public Duration cacheItemTtl = Duration.ofSeconds(30);
  private AppConfigClient client;
  private String clientId;
  private ConfigurationCache cache;

  @Autowired
  private Environment env;

  @Override
  public GetConfigurationResponse getConfigurationResponse(){
    final String application = env.getProperty("appconfig.application");
    final String environment = env.getProperty("appconfig.environment");
    final String config = env.getProperty("appconfig.config");
    cacheItemTtl = Duration.ofSeconds(Long.parseLong(env.getProperty("appconfig.cacheTtlInSeconds")));

    final AppConfigUtility appConfigUtility = new AppConfigUtility(getOrDefault(this::getClient, this::getDefaultClient),
            getOrDefault(this::getConfigurationCache, ConfigurationCache::new),
            getOrDefault(this::getCacheItemTtl, () -> cacheItemTtl),
            getOrDefault(this::getClientId, this::getDefaultClientId));

    return appConfigUtility.getConfiguration(new ConfigurationKey(application, environment, config));
  }

  @Override
  public Boolean isEnableFeature() {
    GetConfigurationResponse configurationResponse = getConfigurationResponse();
    final String appConfigResponse = configurationResponse.content().asUtf8String();
    final JSONObject jsonResponseObject = new JSONObject(appConfigResponse);
    return jsonResponseObject.getBoolean("boolEnableFeature");
  }


  private <T> T getOrDefault(final Supplier<T> optionalGetter, final Supplier<T> defaultGetter) {
    return Optional.ofNullable(optionalGetter.get()).orElseGet(defaultGetter);
  }

  private String getDefaultClientId() {
    return UUID.randomUUID().toString();
  }

  protected AppConfigClient getDefaultClient() {
    return AppConfigClient.create();
  }

  public ConfigurationCache getConfigurationCache() {
    return cache;
  }


  public AppConfigClient getClient() {
    return client;
  }


  public Duration getCacheItemTtl() {
    return cacheItemTtl;
  }

  public String getClientId() {
    return clientId;
  }
}
