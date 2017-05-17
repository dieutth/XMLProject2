package main;

import java.util.List;

public class Service{
	int BEGIN_INDEX = "http://127.0.0.1/".length();
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
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("(:action " + action + "\n");
		sb.append("  :parameters (");
		int len = precondition.size();
		for (int i = 0; i < len; i++){
			if (i != len-1)
				sb.append(precondition.get(i) + " ");
			else{
				sb.append(precondition.get(i));
			}
		}
		len = serviceInput.size();
		sb.append(")\n  :precondition (and\n");
		for (int i = 0; i < len; i++){
			sb.append("  (" + serviceInput.get(i).substring(BEGIN_INDEX) + " " + precondition.get(i) + ")");
		}
		sb.append("  )\n  :effect (and\n");
		for (String e : serviceOutput){
			sb.append("  (" + e.substring(BEGIN_INDEX) + ")");
		}
		sb.append(" )\n)\n");
		return sb.toString();
	}

}