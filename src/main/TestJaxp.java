package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TestJaxp {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String fileLocation = "E:\\OWL-S WEB SERVICES\\Services\\education-novel_author_service.owls\\book_author_service.owls";
		
		DocumentBuilderFactory df;
		DocumentBuilder builder;
		Document document;

		try {
		    // Obtain DocumentBuilder factory
		    df = DocumentBuilderFactory.newInstance();
		    
		    // Get DocumentBuilder instance from factory
		    builder = df.newDocumentBuilder();
		    
		    // Document object instance now is the in-memory representation of the XML file
		    document = builder.parse(fileLocation);
		    NodeList studentNodesList = document.getElementsByTagName("service:Service");
		    
		    for (int i = 0; i < studentNodesList.getLength(); i++) {
		        Element studentItem = (Element) studentNodesList.item(i);
		        System.out.println(studentItem.getAttribute("rdf:ID"));
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		} 
		
		
		
		
	}

}
