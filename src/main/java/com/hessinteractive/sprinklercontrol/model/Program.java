package com.hessinteractive.sprinklercontrol.model;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by hess on 5/3/16.
 */
public class Program {
  int everyNDays;
  List<LocalTime> startTimes;
  List<ZoneRun> zones;

  public int getEveryNDays() {
    return everyNDays;
  }

  public void setEveryNDays(int everyNDays) {
    this.everyNDays = everyNDays;
  }

  public List<LocalTime> getStartTimes() {
    return startTimes;
  }

  public void setStartTimes(List<LocalTime> startTimes) {
    this.startTimes = startTimes;
  }

  public List<ZoneRun> getZones() {
    return zones;
  }

  public void setZones(List<ZoneRun> zones) {
    this.zones = zones;
  }
}
