package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import java.io.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import concertometadatagenerator.util.*;

public class Metadata {

  private ArrayList<Field> fieldList = new ArrayList<Field>();
  private ArrayList<TXModeComponent> txModeComponentList = new ArrayList<TXModeComponent>();
  private ArrayList<RXModeComponent> rxModeComponentList = new ArrayList<RXModeComponent>();
  private ArrayList<AbstractHardwareComponent> aluComponentList = new ArrayList<AbstractHardwareComponent>();

  public ArrayList<TXModeComponent> getTXModeComponentList() {
    return txModeComponentList;
  }

  public ArrayList<RXModeComponent> getRXModeComponentList() {
    return rxModeComponentList;
  }

  public ArrayList<AbstractHardwareComponent> getALUComponentList() {
    return aluComponentList;
  }

  public void readFromJenaModel(OntModel model) {  
    readFieldList(model);
    readALUComponentList(model);
    readTXModeComponentList(model);
    readRXModeComponentList(model);
  }

  public void readFieldList(OntModel model) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT ?fieldName ?fieldType " +
      "WHERE { ?fieldName rdfs:range ?fieldType.\n" +
             " ?fieldName rdfs:domain d2:HardwareComponent.\n" +
             " ?fieldName rdf:type owl:DatatypeProperty.\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    ResultSet results = qexec.execSelect() ;
    while (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode fieldNameNode = result.get("fieldName");
      RDFNode fieldTypeNode = result.get("fieldType");
      Field field = new Field();
      field.setName(StringUtils.getStringAfter(fieldNameNode.toString(), "#"));
      field.setType(StringUtils.getStringAfter(fieldTypeNode.toString(), "#"));
      fieldList.add(field);
    }
    qexec.close();
  }

  public void readALUComponentList(OntModel model) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT ?aluComponent ?latency ?activationCost ?power ?width ?height " +
      "WHERE { ?aluComponent rdf:type d2:ALUComponent.\n" +
             " ?aluComponent d2:latency ?latency.\n" +
             " ?aluComponent d2:activationCost ?activationCost.\n" +
             " ?aluComponent d2:power ?power.\n" +
             " OPTIONAL {?aluComponent d2:width ?width }.\n" +
             " OPTIONAL {?aluComponent d2:height ?height }.\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    ResultSet results = qexec.execSelect() ;
    while (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode aluComponentIndividualNode = result.get("aluComponent");
      String aluComponentIndividualLocalName = StringUtils.getStringAfter(aluComponentIndividualNode.toString(), "#");
      RDFNode latencyNode = result.get("latency");
      RDFNode activationCostNode = result.get("activationCost");
      RDFNode powerNode = result.get("power");
      RDFNode widthNode = result.get("width");
      RDFNode heightNode = result.get("height");
      AbstractHardwareComponent aluComponent = new AbstractHardwareComponent(aluComponentIndividualLocalName);
      aluComponent.setLatency(Integer.parseInt(StringUtils.getStringBefore(latencyNode.toString(), "^^")));
      aluComponent.setActivationCost(Integer.parseInt(StringUtils.getStringBefore(activationCostNode.toString(), "^^")));
      aluComponent.setPower(Float.parseFloat(StringUtils.getStringBefore(powerNode.toString(), "^^")));
      if (widthNode != null)
        aluComponent.setWidth(Integer.parseInt(StringUtils.getStringBefore(widthNode.toString(), "^^")));
      if (heightNode != null)
        aluComponent.setHeight(Integer.parseInt(StringUtils.getStringBefore(heightNode.toString(), "^^")));
      aluComponentList.add(aluComponent);
    }
    qexec.close();
  }

  public void readTXModeComponentList(OntModel model) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT DISTINCT ?basicComponent " +
      "WHERE { ?basicComponent rdf:type d3:BasicComponent.\n" +
             " ?hardwareComponent d2:realizes ?basicComponent.\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    ResultSet results = qexec.execSelect() ;
    while (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode basicComponentIndividualNode = result.get("basicComponent");
      String basicComponentIndividualLocalName = StringUtils.getStringAfter(basicComponentIndividualNode.toString(), "#");
      TXModeComponent txModeComponent = new TXModeComponent(basicComponentIndividualLocalName);
      readTXModeComponentInputPortType(txModeComponent, model, basicComponentIndividualLocalName);
      readTXModeComponentOutputPortType(txModeComponent, model, basicComponentIndividualLocalName);
      readSpecificHardwareComponentList(txModeComponent, model, basicComponentIndividualLocalName);
      txModeComponentList.add(txModeComponent);
    }
    qexec.close();
  }

  public void readTXModeComponentInputPortType(
    TXModeComponent txModeComponent, OntModel model, String basicComponentIndividualLocalName) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT ?inputPort " +
      "WHERE { d1:" + basicComponentIndividualLocalName + " rdf:type ?basicComponentClass.\n" +
               "?basicComponentClass rdfs:subClassOf ?r1.\n" +
               "?r1 rdf:type owl:Restriction.\n" + 
               "?r1 owl:onProperty d3:hasInputPort.\n" + 
               "?r1 owl:allValuesFrom ?inputPort.\n" +
               "FILTER (?inputPort != d3:InputPort).\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    ResultSet results = qexec.execSelect() ;
    if (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode inputPortNode = result.get("inputPort");
      txModeComponent.setInputPortType(StringUtils.getStringAfter(inputPortNode.toString(), "#"));
    }
    qexec.close();
  }

  public void readTXModeComponentOutputPortType(
    TXModeComponent txModeComponent, OntModel model, String basicComponentIndividualLocalName) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT ?outputPort " +
      "WHERE { d1:" + basicComponentIndividualLocalName + " rdf:type ?basicComponentClass.\n" +
               "?basicComponentClass rdfs:subClassOf ?r1.\n" +
               "?r1 rdf:type owl:Restriction.\n" + 
               "?r1 owl:onProperty d3:hasOutputPort.\n" + 
               "?r1 owl:allValuesFrom ?outputPort.\n" +
               "FILTER (?outputPort != d3:OutputPort).\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    ResultSet results = qexec.execSelect() ;
    if (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode outputPortNode = result.get("outputPort");
      txModeComponent.setOutputPortType(StringUtils.getStringAfter(outputPortNode.toString(), "#"));
    }
    qexec.close();
  }

  public void readSpecificHardwareComponentList(
    TXModeComponent txModeComponent, OntModel model, String basicComponentIndividualLocalName) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT ?hardwareComponent ?latency ?activationCost ?power ?width ?height " +
      "WHERE { d1:" + basicComponentIndividualLocalName + " rdf:type d3:BasicComponent.\n" +
             " ?hardwareComponent d2:realizes d1:" + basicComponentIndividualLocalName + ".\n" +
             " ?hardwareComponent d2:latency ?latency.\n" +
             " ?hardwareComponent d2:activationCost ?activationCost.\n" +
             " ?hardwareComponent d2:power ?power.\n" +
             " OPTIONAL {?hardwareComponent d2:width ?width }.\n" +
             " OPTIONAL {?hardwareComponent d2:height ?height }.\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    ResultSet results = qexec.execSelect() ;
    ArrayList<AbstractHardwareComponent> specificHardwareComponentList = new ArrayList<AbstractHardwareComponent>();
    while (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode hardwareComponentIndividualNode = result.get("hardwareComponent");
      String hardwareComponentIndividualLocalName = StringUtils.getStringAfter(hardwareComponentIndividualNode.toString(), "#");
      RDFNode latencyNode = result.get("latency");
      RDFNode activationCostNode = result.get("activationCost");
      RDFNode powerNode = result.get("power");
      RDFNode widthNode = result.get("width");
      RDFNode heightNode = result.get("height");
      AbstractHardwareComponent specificHardwareComponent  = new AbstractHardwareComponent(hardwareComponentIndividualLocalName);
      specificHardwareComponent.setLatency(Integer.parseInt(StringUtils.getStringBefore(latencyNode.toString(), "^^")));
      specificHardwareComponent.setActivationCost(Integer.parseInt(StringUtils.getStringBefore(activationCostNode.toString(), "^^")));
      specificHardwareComponent.setPower(Float.parseFloat(StringUtils.getStringBefore(powerNode.toString(), "^^")));
      if (widthNode != null)
        specificHardwareComponent.setWidth(Integer.parseInt(StringUtils.getStringBefore(widthNode.toString(), "^^")));
      if (heightNode != null)
        specificHardwareComponent.setHeight(Integer.parseInt(StringUtils.getStringBefore(heightNode.toString(), "^^")));
      specificHardwareComponentList.add(specificHardwareComponent);
    }
    txModeComponent.setSpecificHardwareComponentList(specificHardwareComponentList);
    qexec.close();
  }

  public void readRXModeComponentList(OntModel model) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT DISTINCT ?basicComponentClass " +
      "WHERE { ?basicComponentClass rdfs:subClassOf d3:BasicComponent.\n" +
             " FILTER (?basicComponentClass != d3:BasicComponent).\n" +
             " ?basicComponent rdf:type ?basicComponentClass.\n" +
             "  FILTER (CONTAINS(STR(?basicComponent), \"Slow\") || CONTAINS(STR(?basicComponent), \"Fast\"))\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model);
    ResultSet results = qexec.execSelect();
    while (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode basicComponentClassNode = result.get("basicComponentClass");
      String basicComponentClassLocalName = StringUtils.getStringAfter(basicComponentClassNode.toString(), "#");
      RXModeComponent rxModeComponent = new RXModeComponent(basicComponentClassLocalName);
      readFastSlowAbstractHardwareComponentList(rxModeComponent, model, basicComponentClassLocalName);
      rxModeComponentList.add(rxModeComponent);
    }
    qexec.close();
  }

  public void readFastSlowAbstractHardwareComponentList(
    RXModeComponent rxModeComponent, OntModel model, String basicComponentClassLocalName) {
    String queryString = 
      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
      "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
      "PREFIX d1: <http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl#>\n" +
      "PREFIX d2: <http://ece.neu.edu/ontologies/ConcertOlogy.owl#>\n" +
      "PREFIX d3: <http://ece.neu.edu.crf/OBROntology.owl#>\n\n" +
      "SELECT ?basicComponent ?latency ?activationCost ?power ?width ?height " +
      "WHERE { ?basicComponent rdf:type d2:" + basicComponentClassLocalName + ".\n" +
            " ?basicComponent d2:latency ?latency.\n" +
            " ?basicComponent d2:activationCost ?activationCost.\n" +
            " ?basicComponent d2:power ?power.\n" +
            " OPTIONAL {?basicComponent d2:width ?width }.\n" +
            " OPTIONAL {?basicComponent d2:height ?height }.\n" +
      "} ";
    Query query = QueryFactory.create(queryString);
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    ArrayList<AbstractHardwareComponent> fastSlowAbstractHardwareComponentList = new ArrayList<AbstractHardwareComponent>();
    ResultSet results = qexec.execSelect() ;
    while (results.hasNext()) {
      QuerySolution result = results.next();
      RDFNode basicComponentIndividualNode = result.get("basicComponent");
      String basicComponentIndividualLocalName = StringUtils.getStringAfter(basicComponentIndividualNode.toString(), "#");
      RDFNode latencyNode = result.get("latency");
      RDFNode activationCostNode = result.get("activationCost");
      RDFNode powerNode = result.get("power");
      RDFNode widthNode = result.get("width");
      RDFNode heightNode = result.get("height");
      AbstractHardwareComponent fastSlowAbstractHardwareComponent  = new AbstractHardwareComponent(basicComponentIndividualLocalName);
      fastSlowAbstractHardwareComponent.setLatency(Integer.parseInt(StringUtils.getStringBefore(latencyNode.toString(), "^^")));
      fastSlowAbstractHardwareComponent.setActivationCost(
        Integer.parseInt(StringUtils.getStringBefore(activationCostNode.toString(), "^^")));
      fastSlowAbstractHardwareComponent.setPower(Float.parseFloat(StringUtils.getStringBefore(powerNode.toString(), "^^")));
      if (widthNode != null)
        fastSlowAbstractHardwareComponent.setWidth(Integer.parseInt(StringUtils.getStringBefore(widthNode.toString(), "^^")));
      if (heightNode != null)
        fastSlowAbstractHardwareComponent.setHeight(Integer.parseInt(StringUtils.getStringBefore(heightNode.toString(), "^^")));
      fastSlowAbstractHardwareComponentList.add(fastSlowAbstractHardwareComponent);
    }
    rxModeComponent.setFastSlowAbstractHardwareComponentList(fastSlowAbstractHardwareComponentList);
    qexec.close();
  }

  public String getCode() {
    StringBuilder sb = new StringBuilder();
    sb.append("metadata:\n");
    sb.append("  fields:\n");
    for (int i = 0; i < fieldList.size(); i++) {
      sb.append("    " + fieldList.get(i).getName() + ":\n");
      sb.append("      type: " + fieldList.get(i).getType() + "\n");
    }
    sb.append("\n");
    sb.append("  tasks:\n");
    sb.append("    Adder:\n");
    for (int i = 0; i < aluComponentList.size(); i++) {
      sb.append("      " + aluComponentList.get(i).getName() + ":\n");
      sb.append("        latency: " + aluComponentList.get(i).getLatency() + "\n");
      sb.append("        activationCost: " + aluComponentList.get(i).getActivationCost() + "\n");
      sb.append("        power: " + aluComponentList.get(i).getPower() + "\n");
      int width = aluComponentList.get(i).getWidth();
      if (width != 0)
        sb.append("        width: " + width + "\n");
      int height = aluComponentList.get(i).getHeight();
      if (height != 0)
        sb.append("        height: " + height + "\n");
       sb.append("        capability-kind: alu\n");
    }
    sb.append("\n");
    for (int i = 0; i < txModeComponentList.size(); i++) {
      sb.append("    " + txModeComponentList.get(i).getName() + ":\n");
      String inputPortType = txModeComponentList.get(i).getInputPortType();
      if (inputPortType != null)
        sb.append("    input:  " + getInputPortTypeCode(inputPortType) + "\n");
      String outputPortType = txModeComponentList.get(i).getOutputPortType();
      if (outputPortType != null)
        sb.append("    output: " + getOutputPortTypeCode(outputPortType) + "\n");
      ArrayList<AbstractHardwareComponent> specificHardwareComponentList = 
        txModeComponentList.get(i).getSpecificHardwareComponentList();
      for (int j = 0; j < specificHardwareComponentList.size(); j++) {
        sb.append("      " + specificHardwareComponentList.get(j).getName() + ":\n");
        sb.append("        latency: " + specificHardwareComponentList.get(j).getLatency() + "\n");
        sb.append("        activationCost: " + specificHardwareComponentList.get(j).getActivationCost() + "\n");
        sb.append("        power: " + specificHardwareComponentList.get(j).getPower() + "\n");
        int width = specificHardwareComponentList.get(j).getWidth();
        if (width != 0)
          sb.append("        width: " + width + "\n");
        int height = specificHardwareComponentList.get(j).getHeight();
        if (height != 0)
          sb.append("        height: " + height + "\n");
      }
      if (i != txModeComponentList.size() - 1)
        sb.append("\n");
    }
    sb.append("\n");
    for (int i = 0; i < rxModeComponentList.size(); i++) {
      sb.append("    " + rxModeComponentList.get(i).getName() + ":\n");
      ArrayList<AbstractHardwareComponent> fastSlowAbstractHardwareComponentList = 
        rxModeComponentList.get(i).getFastSlowAbstractHardwareComponentList();
      for (int j = 0; j < fastSlowAbstractHardwareComponentList.size(); j++) {
        sb.append("      " + fastSlowAbstractHardwareComponentList.get(j).getName() + ":\n");
        sb.append("        latency: " + fastSlowAbstractHardwareComponentList.get(j).getLatency() + "\n");
        sb.append("        activationCost: " + fastSlowAbstractHardwareComponentList.get(j).getActivationCost() + "\n");
        sb.append("        power: " + fastSlowAbstractHardwareComponentList.get(j).getPower() + "\n");
        int width = fastSlowAbstractHardwareComponentList.get(j).getWidth();
        if (width != 0)
          sb.append("        width: " + width + "\n");
        int height = fastSlowAbstractHardwareComponentList.get(j).getHeight();
        if (height != 0)
          sb.append("        height: " + height + "\n");
      }
      if (i != rxModeComponentList.size() - 1)
        sb.append("\n");
    }
    return sb.toString();
  }

  public String getInputPortTypeCode(String portType) {
    if (portType.equals("IntegerArrayInputPort"))
      return "std::vector<int>";
    else if (portType.equals("IntegerArrayOfArrayInputPort"))
      return "std::vector<std::vector<int>>";
    else if (portType.equals("ComplexArrayInputPort"))
      return "std::vector<std::complex<float>>";
    else if (portType.equals("ComplexArrayOfArrayInputPort"))
      return "std::vector<std::vector<std::complex<float>>";
    return null;
  }

  public String getOutputPortTypeCode(String portType) {
    if (portType.equals("IntegerArrayOutputPort"))
      return "std::vector<int>";
    else if (portType.equals("IntegerArrayOfArrayOutputPort"))
      return "std::vector<std::vector<int>>";
    else if (portType.equals("ComplexArrayOutputPort"))
      return "std::vector<std::complex<float>>";
    else if (portType.equals("ComplexArrayOfArrayOutputPort"))
      return "std::vector<std::vector<std::complex<float>>";
    return null;
  }

}