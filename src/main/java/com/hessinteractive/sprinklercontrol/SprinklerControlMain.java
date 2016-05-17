package com.hessinteractive.sprinklercontrol;

import com.hessinteractive.sprinklercontrol.control.FakeRelayControl;
import com.hessinteractive.sprinklercontrol.control.RealRelayControl;
import com.hessinteractive.sprinklercontrol.control.RelayControl;
import com.hessinteractive.sprinklercontrol.resources.ValveResource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

/**
 * Created by hess on 5/3/16.
 */
public class SprinklerControlMain extends Application<SprinklerControlConfiguration> {
  private static final Logger log = LoggerFactory.getLogger(SprinklerControlMain.class);
  public static ScheduledExecutorService EXECUTOR;

  @Override
  public void initialize(Bootstrap<SprinklerControlConfiguration> bootstrap) {
    super.initialize(bootstrap);
    log.info("System OS: {} {} {}",
             System.getProperty("os.arch"), System.getProperty("os.name"), System.getProperty("os.version"));
  }


  @Override
  public void run(SprinklerControlConfiguration sprinklerControlConfiguration,
                  Environment environment) throws Exception {
    EXECUTOR = environment.lifecycle().scheduledExecutorService("scheduleExecutor").build();

    RelayControl relayControl;

    // only enable the RealRelayControl if we're running this on Arm Linux (a raspberry pi)
    if(System.getProperty("os.name").equals("Linux") &&  System.getProperty("os.arch").equals("arm")) {
      RealRelayControl realRelayControl = new RealRelayControl(sprinklerControlConfiguration.getValveDelay());
      relayControl = realRelayControl;
      environment.lifecycle().manage(realRelayControl);
    } else {
      relayControl = new FakeRelayControl(); // this just logs zone controls
    }

    environment.jersey().register(new ValveResource(relayControl));
    environment.healthChecks().register("sprinkler", new SprinklerHealthCheck());

    setupCORSFilter(sprinklerControlConfiguration, environment);
  }

  private void setupCORSFilter(SprinklerControlConfiguration configuration, Environment environment) {
    FilterRegistration.Dynamic corsFilter = environment.servlets()
        .addFilter("cors-filter", CrossOriginFilter.class);
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    corsFilter.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, "true");
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,PUT,HEAD");
    corsFilter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,"*");
    corsFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
  }


  @Override
  public String getName() {
    return "sprinkler-control";
  }

  public static void main(String[] args) throws Exception {
    log.info("System OS: {} {} {}",
             System.getProperty("os.arch"), System.getProperty("os.name"), System.getProperty("os.version"));

    new SprinklerControlMain().run(args);
  }

}
