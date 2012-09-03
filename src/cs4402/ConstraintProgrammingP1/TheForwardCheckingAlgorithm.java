package cs4402.ConstraintProgrammingP1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** * ********************************************************************************************************************************
 * This Class contains the Forward Checking Algorithm. 
 *  
 * @author 110021201
 * * ********************************************************************************************************************************/
public class TheForwardCheckingAlgorithm {
	CSP csp;
	List<Tuple<Integer,Integer>> solution;
	Map<Integer,List<Tuple<Integer,Integer>>> history;
	int index;
	public TheForwardCheckingAlgorithm(CSP csp) {
		this.csp = csp;
	}
	public void resolve() {
		this.solution = new ArrayList<Tuple<Integer,Integer>>();
		this.index = 0;
		
		this.history = new HashMap<Integer, List<Tuple<Integer,Integer>>>();
		for (int i = 0; i < this.csp.getdomvariable().size(); i++) {
			this.history.put(i, new ArrayList<Tuple<Integer,Integer>>());
		}
		find();
	}
	private void find() {
		
		if (this.csp.getdomvariable().size() <= this.index) {
			return;
		}
	/**
	 * Automatically Selects 
	 * */
		Domain currentDomain = this.csp.getDomain(this.index, this.solution);
		int nextval = currentDomain.getFirst();
		int domaindir = this.csp.getDomainNumber(currentDomain);
		if (nextval == Integer.MIN_VALUE) 
		{
			return;
		}
/**
 * Domain Number 
 * */		
		Tuple<Integer,Integer> solutionTuple = new Tuple<Integer,Integer>(domaindir, nextval);
		this.solution.add(solutionTuple);
		if (index + 1 >= this.csp.getNumberOfdomvariable()) 
		{
			index++;
			return;
		}
		if (this.checkForward(nextval, domaindir)) {
			this.index++;
			this.find();
			if (this.csp.getdomvariable().size() <= this.index) {
				return;
			}
			this.index--;
		}
		if (index > 0) {
			this.history.get(index - 1).add(new Tuple<Integer,Integer>(domaindir, nextval));
		}
		this.solution.remove(solutionTuple);
		
		currentDomain.decreaseValue(nextval);
		
		List<Tuple<Integer,Integer>> currentHistory = this.history.get(this.index);
		for (Tuple<Integer, Integer> entry : currentHistory) {
			this.csp.getdomvariable().get(entry.getfirst()).increaseValue(entry.getSecond());
		}
		currentHistory.clear();	
		
		if (currentDomain.getval().size() < 1) {
			return;
		}
		
			this.find();
	}
	
	private boolean checkForward(int nextValue, int domainIndex) 
	{
		List<Tuple<Integer,Integer>> historyEntry = this.history.get(this.index);
		
		for (SolvingConstraints constraint : this.csp.getConstraintsFor(domainIndex)) 
		{
			boolean checked = false;
		
		for (Tuple<Integer,Integer> solutionTuple : this.solution) {
				if ((constraint.getVariables().getfirst() == domainIndex && constraint.getVariables().getSecond() == solutionTuple.getfirst())
					|| (constraint.getVariables().getfirst() == solutionTuple.getSecond() && constraint.getVariables().getSecond() == domainIndex))
				{
					checked = true;
					break;
				}
			}
			
			if (!checked)
			{			
				int domainNumber = constraint.getVariables().getfirst() == domainIndex ? constraint.getVariables().getSecond() : constraint.getVariables().getfirst();
				Domain domain = this.csp.getdomvariable().get(domainNumber);
					
				for (int domainValue : domain.clone().getval()) {	
					boolean TupleFound = false;
			    	for (Tuple<Integer,Integer> allowedTuple : constraint.getAllowedTuples()) {
			    		if (constraint.getVariables().getfirst() == domainIndex && allowedTuple.getfirst() == nextValue && allowedTuple.getSecond() == domainValue) {
			    			TupleFound = true;
			    			break;
			    		}
			    		else if (constraint.getVariables().getSecond() == domainIndex && allowedTuple.getfirst() == domainValue && allowedTuple.getSecond() == nextValue) {
			    			TupleFound = true;
			    			break;
			    		}
			    	}
			    	if (!TupleFound) {
			    		domain.decreaseValue(domainValue);
			    		historyEntry.add(new Tuple<Integer,Integer>(domainNumber, domainValue));
			    	}
				}
				if (domain.getval().size() < 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	private boolean checkBinaryBranch(int nextValue) 
	{
		if (this.solution.size() < 1) {
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
		if (this.solution == null || this.solution.size() < this.csp.getNumberOfdomvariable()) {
			System.out.println("No solution found.");
		}
		else
		{
			System.out.println("\n" + "Solution for the ForwardChecking Algorithm:");
			for (Tuple<Integer, Integer> solutionTuple : this.solution) {
				System.out.println(String.format("X%d: %d", solutionTuple.getfirst() + 1, solutionTuple.getSecond() + 1));
			}
		}
	}
	
}
