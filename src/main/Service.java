package main;

import java.util.List;

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

}