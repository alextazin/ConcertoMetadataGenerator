package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import concertometadatagenerator.util.*;

public class ModeComponent extends Component {

  private String inputPortType = null;
  private String outputPortType = null;

  public void setInputPortType(String inputPortType) {
    this.inputPortType = inputPortType;
  }

  public void setOutputPortType(String outputPortType) {
    this.outputPortType = outputPortType;
  }

  public String getInputPortType() {
    return inputPortType;
  }

  public String getOutputPortType() {
    return outputPortType;
  }

}