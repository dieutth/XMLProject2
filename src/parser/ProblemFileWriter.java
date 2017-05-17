package parser;
import java.util.List;

public class ProblemFileWriter {
	
	private static final String DOMAIN = "http://127.0.0.1";
	public static String writeProblem(List<String> input, List<String> output){
		StringBuilder sb = new StringBuilder();
		sb.append("(define(problem RequestedService)\n");
		sb.append("(:domain ArbitraryDomain)\n");
		sb.append("(:init(and\n");
		
		for(int i=0; i<input.size(); i++){
			int ind = input.get(i).lastIndexOf("#");
			String concept = input.get(i).substring(ind+1);
			sb.append("\t(" + concept + " ?" + concept.toLowerCase() + ")\n");
		}
		sb.append("      )\n)\n");
		
		sb.append("(:goal ");
		for(int i=0; i<output.size(); i++){
			int ind = output.get(i).lastIndexOf("#");
			String concept = output.get(i).substring(ind+1);
			sb.append("(" + concept + " ?" + concept.toLowerCase() + ")");
		}
		sb.append("\n)\n)");
		return sb.toString();
	}
}