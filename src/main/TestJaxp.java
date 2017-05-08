package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TestJaxp {
	public static void writeToConsole(String a, List<String> params, List<String> ps, List<String> es){
		System.out.println(":action " + a);
		System.out.println(":parameters ");
		for (String p : params){
			System.out.println(p);
		}
		
		System.out.println(":precondition ");
		for (int i = 0; i < ps.size(); i++){
			System.out.println(ps.get(i) + " " + params.get(i));
		}
		
		System.out.println(":effects ");
		for (String e : es){
			System.out.println(e);
		}
	}
	
	public static String createDomainContent(String a, List<String> params,
											List<String> ps, List<String> es){
		StringBuilder sb = new StringBuilder();
		sb.append("(:action " + a + "\n");
		sb.append("  :parameters (");
		int len = params.size();
		for (int i = 0; i < len; i++){
			if (i != len-1)
				sb.append(params.get(i) + " ");
			else{
				sb.append(params.get(i));
			}
		}
		sb.append(")\n  :precondition (and\n");
		
		len = ps.size();
		for (int i = 0; i < len; i++){
			sb.append("(" + ps.get(i) + " " + params.get(i) + ")");
		}
		sb.append("\n  :effect (and ");
		for (String e : es){
			sb.append("(" + e + ")");
		}
		sb.append("\n)\n");
		return sb.toString();
	}
	public static String translate(String fileLocation){
		int BEGIN_INDEX = "http://127.0.0.1/".length();
		DocumentBuilderFactory df;
		DocumentBuilder builder;
		Document document;
		
		//PDDL information
		String action = null;
		List<String> parameters = new ArrayList<String>();
		List<String> preConditions = new ArrayList<String>();
		List<String> effects = new ArrayList<String>();
		try {
		    // Obtain DocumentBuilder factory
		    df = DocumentBuilderFactory.newInstance();
		    
		    // Get DocumentBuilder instance from factory
		    builder = df.newDocumentBuilder();
		    
		    // Document object instance now is the in-memory representation of the XML file
		    document = builder.parse(fileLocation);
		   
		    //get action := service name
		    NodeList nodeList = document.getElementsByTagName("service:Service");
		    Element element = (Element) nodeList.item(0);
		    action = element.getAttribute("rdf:ID");
		    
		    //get parameters and precondition := input and precondition
		    nodeList = document.getElementsByTagName("process:Input");
		    for (int i = 0; i < nodeList.getLength(); i++){
		    	element = (Element) nodeList.item(i);
		    	String param = "?" + element.getAttribute("rdf:ID").toLowerCase();
		    	parameters.add(param);
		    	
		    	String condition = nodeList.item(i).getChildNodes().item(1).getTextContent().substring(BEGIN_INDEX);
//		    	System.out.println(condition);
		    	preConditions.add(condition);
//		    	NodeList nd = nodeList.item(i).getChildNodes();
//		    	 for (int j = 0; j < nd.getLength(); j++){
//		    		 System.out.println("" + j + " " + nd.item(j).toString());
//		    	 }
		    
		    }
		   
		    
		    //get effects := output
		    nodeList = document.getElementsByTagName("process:Output");
		    for (int i = 0; i < nodeList.getLength(); i++){
		    	element = (Element) nodeList.item(i);
		    	effects.add(element.getChildNodes().item(1).getTextContent().substring(BEGIN_INDEX));
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
//		writeToConsole(action, parameters, preConditions, effects);
		System.out.println(createDomainContent(action, parameters, preConditions, effects));
		return createDomainContent(action, parameters, preConditions, effects);
	}
//	public static void main(String[] args) throws FileNotFoundException {
//		// TODO Auto-generated method stub
////		String fileLocation = "E:\\OWL-S WEB SERVICES\\Services\\education-novel_author_service.owls\\book_author_service.owls";
//		//String fileLocation = "E:\\OWL-S WEB SERVICES\\Services\\travel-citycountry_hotel_service.owls\\citycountry_accommodation_service.owls";
//		String fileLocation = "E:\\OWL-S WEB SERVICES\\SWS-TC-1.1\\Services\\BookPriceInStore.owl";
//		
//		translate(fileLocation);
//		
//		
//		
//	}

}
