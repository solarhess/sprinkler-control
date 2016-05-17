package com.hessinteractive.sprinklercontrol.model;

import java.time.Duration;

/**
 * Created by hess on 5/3/16.
 */
public class ZoneRun {
  int valveIndex;
  Duration duration;

  public int getValveIndex() {
    return valveIndex;
  }

  public void setValveIndex(int valveIndex) {
    this.valveIndex = valveIndex;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }
}
