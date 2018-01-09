package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import concertometadatagenerator.util.*;

public class AbstractHardwareComponent extends Component {

  private int latency = 0;
  private int activationCost = 0;
  private float power = 0;
  private int width = 0;
  private int height = 0;

  public AbstractHardwareComponent(String name) {
    this.name = name;
  }

  public void setLatency(int latency) {
    this.latency = latency;
  }

  public void setActivationCost(int activationCost) {
    this.activationCost = activationCost;
  }

  public void setPower(float power) {
    this.power = power;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getLatency() {
    return latency;
  }

  public int getActivationCost() {
    return activationCost;
  }

  public float getPower() {
    return power;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

}