package parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	
	public List parseOntology(String inputFileName){
		
		// use the FileManager to open the ontology from the filesystem
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException( "File: " + inputFileName + " not found"); 
		}
		// read the ontology file
		m.read( in, "" );
		
		List<String> concepts = new ArrayList<String>();
		String s = null;
		for ( OntClass klass : m.listClasses().toList() ) {
			s = klass.toString();
			if (s.contains("#"))
				concepts.add(s);
		}
		concepts.sort(null);
		return concepts;
	}
	
}
