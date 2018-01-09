package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import concertometadatagenerator.util.*;

public class Field {

  private String name;
  private String type;

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

}