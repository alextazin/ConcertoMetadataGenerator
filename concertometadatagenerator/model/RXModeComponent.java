package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import concertometadatagenerator.util.*;

public class RXModeComponent extends ModeComponent {

  ArrayList<AbstractHardwareComponent> fastSlowAbstractHardwareComponentList = new ArrayList<AbstractHardwareComponent>();

  public RXModeComponent(String name) {
    this.name = name;
  }

  public void setFastSlowAbstractHardwareComponentList(ArrayList<AbstractHardwareComponent> fastSlowAbstractHardwareComponentList) {
    this.fastSlowAbstractHardwareComponentList = fastSlowAbstractHardwareComponentList;
  }

  public ArrayList<AbstractHardwareComponent> getFastSlowAbstractHardwareComponentList() {
    return fastSlowAbstractHardwareComponentList;
  }

}