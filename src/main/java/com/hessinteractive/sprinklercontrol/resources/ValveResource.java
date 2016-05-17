package com.hessinteractive.sprinklercontrol.resources;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import com.hessinteractive.sprinklercontrol.control.RelayControl;
import com.hessinteractive.sprinklercontrol.model.Program;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by hess on 5/3/16.
 */
@Path("/valve/")
@Produces({MediaType.APPLICATION_JSON})
public class ValveResource {
  private final RelayControl relayControl;

  public ValveResource(RelayControl relayControl) {
    this.relayControl = relayControl;
  }

  @GET
  @Path("/on/{valveIndex}")
  @Timed
  public String valveIndexOn(@PathParam("valveIndex") Integer id) {
    this.relayControl.setRelayOn(id.intValue());
    return "on "+ id;
  }

  @GET
  @Path("/off")
  @Timed
  public String relayOff() {
    this.relayControl.setAllOff();
    return "off";
  }

  @GET
  @Timed
  public String whichValve() {
    return "valve " + this.relayControl.getRelayOnIndex();
  }

}
