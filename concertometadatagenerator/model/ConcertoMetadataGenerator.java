package concertometadatagenerator.model;

import java.util.*;
import java.lang.*;
import java.io.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;

public class ConcertoMetadataGenerator {

  private OntModel model;

  public ConcertoMetadataGenerator(String ontologyPath) {
    model = ModelFactory.createOntologyModel();
    OntDocumentManager dm = model.getDocumentManager();
    dm.addAltEntry("http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl",
                   ontologyPath + "ConcertOlogy3_Abox.owl");
    dm.addAltEntry("http://ece.neu.edu/ontologies/ConcertOlogy.owl",
                   ontologyPath + "ConcertOlogy3.owl");
    dm.addAltEntry("http://ece.neu.edu.crf/OBROntology.owl",
                   ontologyPath + "OBROntology.owl");
    model.read("http://ece.neu.edu/ontologies/ConcertOlogy_ABox.owl");
  }

  public void generateCode() throws IOException {
    Metadata metadata = new Metadata();
    metadata.readFromJenaModel(model);
    String metadataCodeString = metadata.getCode();
    writeCode("metadata.txt", metadataCodeString);
  }

  private void writeCode(String outputFileName, String codeString) throws IOException {
    File file = new File(outputFileName);
    if (!file.exists())
      file.createNewFile();
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(codeString, 0, codeString.length());
    bw.close();
  }

  public static void main(String[] args) {
    try {
      String ontologyPath = args[0];
      ConcertoMetadataGenerator concertoMetadataGenerator = new ConcertoMetadataGenerator(ontologyPath);
      concertoMetadataGenerator.generateCode();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}