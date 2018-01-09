package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import concertometadatagenerator.util.*;

public class Component {

  protected String name;

  public String getName() {
    return name;
  }

}