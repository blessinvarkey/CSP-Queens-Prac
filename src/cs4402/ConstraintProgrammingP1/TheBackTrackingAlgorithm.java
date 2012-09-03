package cs4402.ConstraintProgrammingP1;

/**
 *In this class, we deal with the backtracking algorithm 
**/

import java.util.ArrayList;
import java.util.List;

/**
 * This Class contains the Forward Checking Algorithm. 
 *  
 * @author 110021201
 * */
public class TheBackTrackingAlgorithm 
{
	CSP csp;
	List<Tuple<Integer,Integer>> solution;  
	int dir;
	public TheBackTrackingAlgorithm(CSP csp) 
	{
		this.csp = csp;
	}
	 void resolve() {
		this.solution = new ArrayList<Tuple<Integer,Integer>>();
		this.dir = 0;
		search(null, 0);
	}
	private void search(Domain domain, int domaindir) {
		if (domain == null) {
			domain = this.csp.getDomain(dir, this.solution);
			domaindir = this.csp.getDomainNumber(domain);
			domain = domain.clone();
		}
		int nextValue = domain.getFirst();
		if (nextValue == Integer.MIN_VALUE) {
			return;
		}
		System.out.println("Finding solution..: " + nextValue);
		if (this.checkSolution(nextValue, domaindir)) {
			System.out.println("Found Solution!" + '\n' );
			Tuple<Integer,Integer> solutionTuple = new Tuple<Integer,Integer>(domaindir, nextValue);
			this.solution.add(solutionTuple);
			this.dir++;
			this.search(null, 0);
			if (dir >= this.csp.getNumberOfdomvariable()) {
				return;
			}
			this.solution.remove(solutionTuple);
			this.dir--;
		}
			domain.decreaseValue(nextValue);
			this.search(domain, domaindir);
			}
	
	private boolean checkSolution(int nextValue, int domainIndex) {
		if (this.solution.size() < 1) {
			return true;
		}
			for (Tuple<Integer,Integer> pair : this.solution) {
			SolvingConstraints constraint = this.csp.getConstraint(domainIndex, pair.getfirst());
			if (constraint != null) {
				boolean pairFound = false;
		    	for (Tuple<Integer,Integer> allowedPair : constraint.getAllowedTuples()) {
		    		if (constraint.getVariables().getfirst() == domainIndex && allowedPair.getfirst() == nextValue && allowedPair.getSecond() == pair.getSecond()) {
		    			pairFound = true;
		    			break;
		    		}
		    		else if (constraint.getVariables().getSecond() == domainIndex && allowedPair.getfirst() == pair.getSecond() && allowedPair.getSecond() == nextValue) {
		    			pairFound = true;
		    			break;
		    		}
		    	}
		    	if (!pairFound) {
		    		return false;
		    	}
			}
		}
		return true;
	}
		@SuppressWarnings("unused")
	private boolean checkBinaryBranch(int nextValue) 
	{
		if (this.solution.size() < 1) 
		{
			return true;
		}
		for (SolvingConstraints constraint : this.csp.getConstraintsFor(this.solution.size())) {
		    	int firstValue = this.solution.get(constraint.getVariables().getfirst()).getSecond();
		    	boolean TupleFound = false;
		    	for (Tuple<Integer,Integer> allowedTuple : constraint.getAllowedTuples()) {
		    		if (allowedTuple.getfirst() == firstValue && allowedTuple.getSecond() != nextValue) {
		    			TupleFound = true;
		    			break;
		    		}
		    	}
		    	if (!TupleFound) {
		    		return false;
		    	}
	    }
		return true;
	}
		public void printSolution() {
		if (this.solution == null || this.solution.size() < this.csp.getNumberOfdomvariable()) 
		{
			System.out.println("No solution found.");
		}
		else
		{
			System.out.println("\n"+"Solution for the Backtracking Algorithm:");
			for (Tuple<Integer, Integer> solutionTuple : this.solution) {
				System.out.println(String.format("X%d: %d", solutionTuple.getfirst() + 1, solutionTuple.getSecond() + 1));
			}
		}
	}
}
