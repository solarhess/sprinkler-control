package com.hessinteractive.sprinklercontrol.model;

import java.util.List;

import javax.ws.rs.Produces;

/**
 * Created by hess on 5/3/16.
 */
public class SprinklerSystemSchedule {
  boolean enabled;
  List<Program> programs;
}
