package com.hessinteractive.sprinklercontrol;

import com.hessinteractive.sprinklercontrol.model.ZoneDescription;

import io.dropwizard.Configuration;
import io.dropwizard.util.Duration;

import org.hibernate.validator.constraints.NotEmpty;
import java.io.File;
import java.util.List;

/**
 * Created by hess on 5/3/16.
 */
public class SprinklerControlConfiguration extends Configuration {
  @NotEmpty
  private String template;

  private Duration valveDelay;

  private File scheduleFile;

  public List<ZoneDescription> getZoneDescriptions() {
    return zoneDescriptions;
  }

  public void setZoneDescriptions(
      List<ZoneDescription> zoneDescriptions) {
    this.zoneDescriptions = zoneDescriptions;
  }

  private List<ZoneDescription> zoneDescriptions;

  public File getScheduleFile() {
    return scheduleFile;
  }

  public void setScheduleFile(File scheduleFile) {
    this.scheduleFile = scheduleFile;
  }

  public String getTemplate() {
    return template;
  }

  public Duration getValveDelay() {
    return valveDelay;
  }

  public void setValveDelay(Duration valveDelay) {
    this.valveDelay = valveDelay;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

}
