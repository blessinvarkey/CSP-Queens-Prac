package cs4402.ConstraintProgrammingP1;

/**
 * This is the solving Constraints Class. 
 * @author blessinvarkey  
 * */


import java.util.ArrayList;
import java.util.List;


public class SolvingConstraints {

	private Tuple<Integer, Integer> variables;
	private List<Tuple<Integer, Integer>> allowedTuples;
	
	public SolvingConstraints(int variable1, int variable2) {
		this.variables = new Tuple<Integer, Integer>(variable1, variable2);
		this.allowedTuples = new ArrayList<Tuple<Integer,Integer>>();
	}
	
	public void addConstraint(int variable1, int variable2) {
		this.allowedTuples.add(new Tuple<Integer, Integer>(variable1, variable2));
	}
	
	public Tuple<Integer, Integer> getVariables() {
		return this.variables;
	}
	
	public List<Tuple<Integer, Integer>> getAllowedTuples() {
		return this.allowedTuples;
	}
}

