package solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import main.Service;

public class PDDLSolver {
	public static String solve(List<String> initial, List<String> goal, 
								List<Service> services, OntModel m){
		int totalLevel = services.size();
		Stack<Service> serviceStack = new Stack<Service>();
		Stack<List<Service>> serviceQueue = new Stack<List<Service>>(); 
		List<String> states = new ArrayList<String>(initial);
		int n = 0;
		boolean flag = false;
		do {
			List<Service> serviceList = new ArrayList<Service>();
			if (serviceQueue.size() != totalLevel){
				for (Service service : services){
					if (serviceStack.contains(service))
						continue;
					if (isCovered(service.getServiceInput(), states, m)){
						if (isCovered(goal, service.getServiceOutput(), m)){
							flag = true;
							serviceStack.push(service);
							break;
						}
						serviceList.add(service);
					}
				}
			}
			if (flag)
				break;
			serviceQueue.push(serviceList);
			
			if (serviceList.size() != 0){
				Service sv = serviceList.get(0);
				serviceStack.push(sv);
				for (String s : sv.getServiceOutput())
					states.add(s);

			}else{
				while (serviceList.isEmpty() && !serviceQueue.isEmpty()){
					serviceQueue.pop();
					if (!serviceStack.isEmpty())
						serviceStack.pop();
					if (!serviceQueue.isEmpty()){
						serviceList = serviceQueue.peek();
						Service sv = serviceList.remove(0);
						n = states.size();
						states = states.subList(0, n - sv.getServiceOutput().size());
					}
				}
				if (!serviceList.isEmpty()){
					serviceStack.push(serviceList.get(0));
					for (String s : serviceList.get(0).getServiceOutput())
						states.add(s);
				}
			}
			
		}while (!isCovered(goal, states, m) && !serviceQueue.isEmpty());
		
		
		if (flag || isCovered(goal, states, m)){
			StringBuilder sb = new StringBuilder();
			sb.append("Solution found: \n");
			for (Service s : serviceStack)
				sb.append(s.getAction() + "\n");
			return sb.toString();
		}
		else{
			return "No solution found!";
		}
	}
	
	public static boolean isCovered(List<String> s1, List<String> s2, OntModel m){
		for (String s : s1){
			boolean flag = true;
			OntClass oc = m.getOntClass(s);
			if (oc.hasSuperClass()){
				for (Iterator<OntClass> i = oc.listSuperClasses(); i.hasNext();){
					String cla = ((OntClass)i.next()).toString();
					if (s2.contains(cla)){
						flag = false;
						break;
					}
				}
				
			}
			if (flag && !s2.contains(oc.toString()))
				return false;
		}
		return true;
	}
}
