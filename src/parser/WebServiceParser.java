package parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import main.Service;

public class WebServiceParser {

	public static String createDomainContent(Service sv){
		String a = sv.getAction();
		List<String> params = sv.getServiceInput();
		List<String> ps = sv.getPrecondition();
		List<String> es = sv.getServiceOutput();
		
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
	

	public static Service parseService(String fileLocation){
		
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
		    	
		    	String condition = nodeList.item(i).getChildNodes().item(1).getTextContent();
		    	preConditions.add(condition);		    
		    }
		   
		    
		    //get effects := output
		    nodeList = document.getElementsByTagName("process:Output");
		    for (int i = 0; i < nodeList.getLength(); i++){
		    	element = (Element) nodeList.item(i);
		    	effects.add(element.getChildNodes().item(1).getTextContent());//.substring(BEGIN_INDEX));
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		Service sv = new Service(preConditions, effects, parameters, action);
		return sv;
	}
	
}
