package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import concertometadatagenerator.util.*;

public class TXModeComponent extends ModeComponent {

  ArrayList<AbstractHardwareComponent> specificHardwareComponentList = new ArrayList<AbstractHardwareComponent>();

  public TXModeComponent(String name) {
    this.name = name;
  }

  public void setSpecificHardwareComponentList(ArrayList<AbstractHardwareComponent> specificHardwareComponentList) {
    this.specificHardwareComponentList = specificHardwareComponentList;
  }

  public ArrayList<AbstractHardwareComponent> getSpecificHardwareComponentList() {
    return specificHardwareComponentList;
  }

}