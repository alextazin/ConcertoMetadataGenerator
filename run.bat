SET CLASSPATH=%CLASSPATH%;.;C:\apache-jena-3.4.0\lib\jena-arq-3.4.0.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\jena-core-3.4.0.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\jena-base-3.4.0.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\jena-iri-3.4.0.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\httpcore-4.4.6.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\httpclient-4.5.3.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\jena-shaded-guava-3.4.0.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\jsonld-java-0.10.0.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\jackson-annotations-2.8.0.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\jackson-core-2.8.6.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\libthrift-0.9.3.jar
SET CLASSPATH=%CLASSPATH%;C:\apache-jena-3.4.0\lib\commons-lang3-3.4.jar
SET CLASSPATH=%CLASSPATH%;C:\slf4j-1.7.6\slf4j-api-1.7.6.jar
SET CLASSPATH=%CLASSPATH%;C:\slf4j-1.7.6\slf4j-simple-1.7.6.jar
SET CLASSPATH=%CLASSPATH%;C:\commons-logging-1.1.3\commons-logging-1.1.3.jar
javac concertometadatagenerator\model\ConcertoMetadataGenerator.java
java concertometadatagenerator.model.ConcertoMetadataGenerator "file:C:/Users/Home User/Desktop/OOPConcertoMetadataGenerator/"
pause