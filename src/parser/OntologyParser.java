package main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class OntologyParser {
	public static void main(String[] args) {
		parseOntology("E:\\OWL-S WEB SERVICES\\SWS-TC-1.1\\Ontology\\Concepts.owl");
	}
	public static void writeToConsole(List<String> concepts){
		System.out.println(":init (and ");
		for (String c : concepts){
			System.out.println("(" + c + " ?" + c.toLowerCase() + ")");
		}
		
		System.out.println(":goal ");
		
	}
	
	public static void parseOntology(String inputFileName){
		OntDocumentManager mgr = new OntDocumentManager();
		OntModelSpec s = new OntModelSpec( OntModelSpec.RDFS_MEM_RDFS_INF );
		s.setDocumentManager( mgr );
		OntModel m = ModelFactory.createOntologyModel( s, null );
		// use the FileManager to open the ontology from the filesystem
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
		throw new IllegalArgumentException( "File: " + inputFileName + " not found"); }
		// read the ontology file
		m.read( in, "" );
		
		// write it to standard out (RDF/XML)
//		for ( OntClass klass : m.listClasses().toList() ) {
//            System.out.println( klass );
//        }
		String NS = "http://127.0.0.1/ontology/Concepts.owl#";
		OntClass paper = m.getOntClass(NS + "Financial_Institution");
		System.out.println("Class " + paper.toString());
		System.out.println("Its subclass: " + paper.getSubClass());
		System.out.println("Its superclass: " + paper.getSuperClass().toString());
//		Map map = m.getNsPrefixMap();
//		for (Object key : map.keySet())
//			System.out.println(key + "; " + map.get(key));
		
		/*
		 * Generate problem file
		 */
		List<String> concepts = new ArrayList<String>();
		int count = 0;
		for ( OntClass klass : m.listClasses().toList() ) {
			concepts.add(klass.toString());
			count++;
			if (count > 10)
				break;
				
		}
		
		writeToConsole(concepts);
	}
	
}
