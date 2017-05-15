package parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class OntologyParser {
	private OntModel m ;
	
	public OntologyParser(){
		OntDocumentManager mgr = new OntDocumentManager();
		OntModelSpec s = new OntModelSpec( OntModelSpec.RDFS_MEM_RDFS_INF );
		s.setDocumentManager( mgr );
		m = ModelFactory.createOntologyModel( s, null );
	}
	public OntModel getOntModel(){
		return m;
	}
	public static void writeToConsole(List<String> concepts){
		System.out.println(":init (and ");
		for (String c : concepts){
			System.out.println("(" + c + " ?" + c.toLowerCase() + ")");
		}
		
		System.out.println(":goal ");
		
	}
	
	public List parseOntology(String inputFileName){
		
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
		OntClass oc = m.getOntClass(NS + "CreditCard");
		if (oc.hasSuperClass()){
			for (Iterator<OntClass> i = oc.listSuperClasses(); i.hasNext();){
				System.out.println(((OntClass)i.next()).toString());
			}
		}
		/*
		 * Generate problem file
		 */
		List<String> concepts = new ArrayList<String>();
		int count = 0;
		for ( OntClass klass : m.listClasses().toList() ) {
			concepts.add(klass.toString());
		}
		concepts.sort(null);
		return concepts;
	}
	public static void main(String[] args) {
		new OntologyParser().parseOntology("E:\\OWL-S WEB SERVICES\\SWS-TC-1.1\\Ontology\\Concepts.owl");
	}
}
