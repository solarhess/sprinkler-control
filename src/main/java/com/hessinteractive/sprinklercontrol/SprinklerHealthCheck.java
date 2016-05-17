package com.hessinteractive.sprinklercontrol;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by hess on 5/3/16.
 */
public class SprinklerHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    if (false) {
      return Result.unhealthy("its broken");
    }
    return Result.healthy();
  }

}
