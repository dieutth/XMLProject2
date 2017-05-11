package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import main.Service;

public class PDDLSolver {
	public static boolean solve(List<String> initial, List<String> goal, List<Service> services){
		
		int totalLevel = services.size();
		Stack<Service> serviceStack = new Stack<Service>();
		Stack<List<Service>> serviceQueue = new Stack<List<Service>>(); 
		List<String> states = new ArrayList<String>(initial);
		int n = 0;
		do {
			
			List<Service> serviceList = new ArrayList<Service>();
			if (serviceQueue.size() != totalLevel){
				for (Service service : services){
					if (serviceStack.contains(service))
						continue;
					if (isCovered(service.getServiceInput(), states)){
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
			
		}while (!isCovered(goal, states) && !serviceQueue.isEmpty());
		
		if (isCovered(goal, states)){
			System.out.println("Solution: ");
			for (Service s : serviceStack)
				System.out.println(s.getAction());
			return true;
		}
		else{
			System.out.println("No solution found!");
			return false;
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
		
		Service service3= new Service(Arrays.asList("price".split(" ")), 
				Arrays.asList("discount".split(" ")),
				"service 3");
		
		Service service4= new Service(Arrays.asList("discount".split(" ")), 
				Arrays.asList("paid".split(" ")),
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
		System.out.println(solve(in, out, services));
		
	}
	
	public static boolean isCovered(List<String> s1, List<String> s2){
		for (String s : s1)
			if (!s2.contains(s))
				return false;
		return true;
	}
}
