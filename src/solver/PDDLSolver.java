package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import main.Service;

public class PDDLSolver {
	
	private static List extendState(List<String> state, OntModel m, List<String> possibleInputs){
		List<String> newState = new ArrayList<String>(state);
		String cla = "";
		
		for (String s : state){
			OntClass oc = m.getOntClass(s);
			if (oc.hasSuperClass()){
				for (Iterator<OntClass> i = oc.listSuperClasses(); i.hasNext();){
					cla = ((OntClass)i.next()).toString();
					if (!newState.contains(cla))
						newState.add(cla);
				}
			}
			if (oc.hasSubClass()){
				for (Iterator<OntClass> i = oc.listSubClasses(); i.hasNext();){
					cla = ((OntClass)i.next()).toString();
					if (!newState.contains(cla) && possibleInputs.contains(cla)){
						newState.add(cla);
						OntClass tmp = m.getOntClass(cla);
						if (tmp.hasSuperClass()){
							for (Iterator<OntClass> i2 = oc.listSuperClasses(); i2.hasNext();){
								cla = ((OntClass)i2.next()).toString();
								if (!newState.contains(cla))
									newState.add(cla);
							}
						}
					}
				}
			}
		}
		return newState;
	}
	public static String solve(List<String> initial, List<String> goal, 
								List<Service> services, OntModel m){
		int totalLevel = services.size();
		Stack<Service> serviceStack = new Stack<Service>();
		Stack<List<Service>> serviceQueue = new Stack<List<Service>>(); 
		List<String> states = new ArrayList<String>(initial);
//		List<String> possibleInput = new ArrayList<String>();
//		
//		for (Service service : services){
//			for (String s : service.getServiceInput())
//				if (!possibleInput.contains(s))
//					possibleInput.add(s);
//		}
			
//		states = extendState(initial, m, possibleInput);
//		System.out.println("InitialState");
//		for (String s : states)
//			System.out.println(s);
		int n = 0;
		do {
			
			List<Service> serviceList = new ArrayList<Service>();
			if (serviceQueue.size() != totalLevel){
				for (Service service : services){
					if (serviceStack.contains(service))
						continue;
					if (isCovered2(service.getServiceInput(), states, m)){
						serviceList.add(service);
					}
				}
			}
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
			
		}while (!isCovered2(goal, states, m) && !serviceQueue.isEmpty());
		
		
		if (isCovered(goal, states)){
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> in = Arrays.asList("book author".split(" "));
		List<String> out = Arrays.asList("location".split(" "));
		
		Service service1 = new Service(Arrays.asList("book author".split(" ")), 
				Arrays.asList("isbn".split(" ")),
				"service 1");
		
		Service service2 = new Service(Arrays.asList("isbn".split(" ")), 
				Arrays.asList("price".split(" ")),
				"service 2");
		
		Service service3= new Service(Arrays.asList("isbn".split(" ")), 
				Arrays.asList("discount".split(" ")),
				"service 3");
		
		Service service4= new Service(Arrays.asList("price discount".split(" ")), 
				Arrays.asList("location".split(" ")),
				"service 4");
		Service service5= new Service(Arrays.asList("paid price".split(" ")), 
				Arrays.asList("location".split(" ")),
				"service 5");
		
		List<Service> services = new ArrayList<Service>();
		services.add(service3);
		services.add(service5);
		services.add(service4);
		services.add(service2);
		services.add(service1);
//		System.out.println(solve(in, out, services));
		
	}
	
	public static boolean isCovered(List<String> s1, List<String> s2){
		for (String s : s1)
			if (!s2.contains(s))
				return false;
		return true;
	}
	
	public static boolean isCovered2(List<String> s1, List<String> s2, OntModel m){
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
