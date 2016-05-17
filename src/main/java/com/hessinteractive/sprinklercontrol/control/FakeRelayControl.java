package com.hessinteractive.sprinklercontrol.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by hess on 5/4/16.
 */
public class FakeRelayControl implements RelayControl {
  private static final Logger log = LoggerFactory.getLogger(FakeRelayControl.class);
  int relayOnIndex = -1;

  @Override
  public void setAllOff() {
    log.info("All Relays Off");
    relayOnIndex = -1;
  }

  @Override
  public void setRelayOn(int relay) {
    log.info("Relay On {}", Integer.valueOf(relay));
    this.relayOnIndex = relay;
  }

  @Override
  public int getRelayOnIndex() {
    return relayOnIndex;
  }
}
