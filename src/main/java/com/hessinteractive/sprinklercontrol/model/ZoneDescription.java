package com.hessinteractive.sprinklercontrol.model;

/**
 * Created by hess on 5/3/16.
 */
public class ZoneDescription {
  private int valveIndex;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getValveIndex() {
    return valveIndex;
  }

  public void setValveIndex(int valveIndex) {
    this.valveIndex = valveIndex;
  }
}
