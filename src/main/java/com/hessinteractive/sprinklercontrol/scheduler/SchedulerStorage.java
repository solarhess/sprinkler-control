package com.hessinteractive.sprinklercontrol.scheduler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hessinteractive.sprinklercontrol.model.SprinklerSystemSchedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.jackson.Jackson;
import io.dropwizard.lifecycle.Managed;

import java.io.File;

/**
 * Created by hess on 5/3/16.
 */
public class SchedulerStorage implements Managed {
  private static final Logger log = LoggerFactory.getLogger(SchedulerStorage.class);

  private final File scheduleStorageFile;
  private final ObjectReader jsonReader;
  private final ObjectWriter jsonWriter;
  private SprinklerSystemSchedule schedule;

  public SchedulerStorage(File scheduleStorageFile) {
    this.scheduleStorageFile = scheduleStorageFile;
    ObjectMapper om = Jackson.newObjectMapper();
    this.jsonReader = om.readerFor(SprinklerSystemSchedule.class);
    this.jsonWriter = om.writerFor(SprinklerSystemSchedule.class);
    loadSchedule();
  }

  private void loadSchedule() {
    try {
      this.schedule = jsonReader.readValue(scheduleStorageFile);
    } catch(Exception e) {
      log.debug("Exception while reading json schedule", e);
      this.schedule = new SprinklerSystemSchedule();
    }
  }
  private void saveSchedule() {
    try {
      jsonWriter.writeValue(scheduleStorageFile, this.schedule);
    } catch(Exception e) {
      log.debug("Exception while writing json schedule", e);
    }
  }


  public SprinklerSystemSchedule getSchedule(){
    if(this.schedule == null) {
      loadSchedule();
    }
    return schedule;
  }

  public void setSchedule(SprinklerSystemSchedule schedule) {
    this.schedule = schedule;
    saveSchedule();
  }

  @Override
  public void start() throws Exception {
    loadSchedule();
  }

  @Override
  public void stop() throws Exception {
    saveSchedule();
  }
}
