package com.hessinteractive.sprinklercontrol.control;

/**
 * Created by hess on 5/3/16.
 */
public interface RelayControl {
  public void setAllOff();
  public void setRelayOn(int relay);
  public int getRelayOnIndex();
}

