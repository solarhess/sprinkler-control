package com.hessinteractive.sprinklercontrol.control;

import com.hessinteractive.sprinklercontrol.SprinklerControlMain;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.impl.PinImpl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import static com.pi4j.io.gpio.RaspiPin.*;
import static com.pi4j.io.gpio.RaspiPin.GPIO_02;
import static com.pi4j.io.gpio.RaspiPin.GPIO_03;
import static com.pi4j.io.gpio.RaspiPin.GPIO_04;
import static com.pi4j.io.gpio.RaspiPin.GPIO_05;
import static com.pi4j.io.gpio.RaspiPin.GPIO_06;
import static com.pi4j.io.gpio.RaspiPin.GPIO_22;
import static com.pi4j.io.gpio.RaspiPin.GPIO_23;
import static com.pi4j.io.gpio.RaspiPin.GPIO_24;
import static com.pi4j.io.gpio.RaspiPin.GPIO_25;
import static com.pi4j.io.gpio.RaspiPin.GPIO_26;
import static com.pi4j.io.gpio.RaspiPin.GPIO_27;
import static com.pi4j.io.gpio.RaspiPin.GPIO_28;
import static com.pi4j.io.gpio.RaspiPin.GPIO_29;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.util.Duration;

/**
 * Created by hess on 5/3/16.
 */
public class RealRelayControl implements RelayControl, Managed {
  private static final Pin[] GPIO_PINS = new Pin[] {
      GPIO_00,GPIO_01,GPIO_02,GPIO_03,GPIO_04,GPIO_05,GPIO_06,
      GPIO_21,GPIO_22,GPIO_23,GPIO_24,GPIO_25,GPIO_26,GPIO_27,GPIO_28,GPIO_29
  };

  private final GpioController gpio;
  private final Duration valveDelay;
  private final List<GpioPinDigitalOutput> outputs;
  private ScheduledFuture<?> inProgressSchedule = null;

  public RealRelayControl(Duration valveDelay) {
    this.valveDelay = valveDelay;
    gpio = GpioFactory.getInstance();
    outputs = Arrays.stream(GPIO_PINS)
        .map((pinId) -> {
          GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(pinId, pinId.getName(), PinState.HIGH);
          pin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
          return pin;
        })
        .collect(Collectors.toList());
  }

  @Override
  public int getRelayOnIndex() {
    for (int i = 0; i < outputs.size(); i++) {
      if(outputs.get(i).isLow()) { // low is on
        return i;
      }
    }
    return -1;
  }

  @Override
  public void setAllOff() {
    outputs.stream().forEach((pin)-> closeValve(pin)); // hi is off
  }

  private void closeValve(GpioPinDigitalOutput pin) {
    pin.setState(true); // HI is valve closed
  }

  private void openValve(int relayIndex) {
    outputs.get(relayIndex).setState(false); // LOW is valve open
  }

  @Override
  public synchronized void setRelayOn(int relayIndex) {
    boolean delayValveOn = false;
    if(getRelayOnIndex() >= 0) {
      delayValveOn = true;
    }

    if(inProgressSchedule != null) {
      inProgressSchedule.cancel(false);
    }
    setAllOff();

    if(delayValveOn) {
      this.inProgressSchedule = SprinklerControlMain.EXECUTOR.schedule(() -> {
        setAllOff();
        openValve(relayIndex);
      }, valveDelay.getQuantity(), valveDelay.getUnit());
    } else {
      openValve(relayIndex);
    }
  }



  private static Pin createDigitalPin(int address, String name) {
    return new PinImpl("RaspberryPi GPIO Provider", address, name, EnumSet
        .of(PinMode.DIGITAL_INPUT, PinMode.DIGITAL_OUTPUT), PinPullResistance.all());
  }

  @Override
  public void start() throws Exception {

  }

  @Override
  public void stop() throws Exception {
    setAllOff();
    gpio.shutdown();
  }
}
