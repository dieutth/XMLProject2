package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

public class Service{
	private List<String> serviceInput;
	private List<String> serviceOutput;
	private List<String> precondition;
	private String action;

	public Service(List<String> i, List<String> o, List<String> p, String action){
		setServiceInput(i);
		setServiceOutput(o);
		setPrecondition(p);
		setAction(action);
	}
	public Service(List<String> i, List<String> o, String action){
		setServiceInput(i);
		setServiceOutput(o);
		setAction(action);
	} 
	public List<String> getServiceInput() {
		return serviceInput;
	}
	public void setServiceInput(List<String> serviceInput) {
		this.serviceInput = serviceInput;
	}
	public List<String> getServiceOutput() {
		return serviceOutput;
	}
	public void setServiceOutput(List<String> serviceOutput) {
		this.serviceOutput = serviceOutput;
	}
	public List<String> getPrecondition() {
		return precondition;
	}
	public void setPrecondition(List<String> precondition) {
		this.precondition = precondition;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public void extendService(OntModel m){
		List<String> extendedInput = new ArrayList<String>(serviceInput);
		for (String s : serviceInput){
			String cla = "";
			OntClass oc = m.getOntClass(s);
			if (oc.hasSuperClass()){
				for (Iterator<OntClass> i = oc.listSuperClasses(); i.hasNext();){
					cla = ((OntClass)i.next()).toString();
					if (!extendedInput.contains(cla))
						extendedInput.add(cla);
					}
				}
		}
		setServiceInput(extendedInput);
		
		List<String> extendedOutput = new ArrayList<String>(serviceOutput);
		for (String s : serviceOutput){
			String cla = "";
			OntClass oc = m.getOntClass(s);
			if (oc.hasSuperClass()){
				for (Iterator<OntClass> i = oc.listSuperClasses(); i.hasNext();){
					cla = ((OntClass)i.next()).toString();
					if (!extendedInput.contains(cla))
						extendedInput.add(cla);
					}
				}
		}
		setServiceOutput(extendedOutput);
	}
}