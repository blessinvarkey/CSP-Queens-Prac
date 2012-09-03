package cs4402.ConstraintProgrammingP1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** * ********************************************************************************************************************************
 * This is the Constraint Satisfaction Problem Class. 
 * The class contains the OrderType,   
 * @author 110021201
 * * * ********************************************************************************************************************************/
public class CSP {
	public List<Domain> Domainvariable;
	public List<Domain> domvariable;
	public Map<Integer,Integer> DomainOrdering;
	public List<SolvingConstraints> constraints;
	public OrderType orderType;
	
	public CSP(int[][] DomainBounds, ArrayList<?> constraintval, Map<Integer,Integer> order, String orderType)	{
		this.Domainvariable = this.parsedomvariable(DomainBounds);
		this.domvariable = this.copyDomainvariable(this.Domainvariable);
		this.constraints = this.parseConstraints(constraintval);
		this.DomainOrdering = order;
		this.orderType = this.parseOrderType(orderType);
	}
/** * *****************************************************************************************************************************
 * @category Enum
 * The Enum OrderType consists of the constants, 
 * namely DEFAULT, STATIC & DYNAMIC 
 * The switch case has been used to define the index dir where, 
 *  * 
 * ********************************************************************************************************************************/
	public enum OrderType {
		DEFAULT,
		STATIC,
		DYNAMIC	}

	public void resettype() {
		this.domvariable = copyDomainvariable(this.Domainvariable);
	}
	public Domain getDomain(int dir, List<Tuple<Integer,Integer>> result) {
		if (dir < this.domvariable.size()) {
			switch (this.orderType) {
				case STATIC:
					return this.domvariable.get(this.DomainOrdering.get(dir));
				case DYNAMIC:
					List<Domain> copiedList = this.copyAndRemove(this.domvariable, 1, result);
					Collections.sort(copiedList);
					if (copiedList.size() > 0) {
						return copiedList.get(0);
					}
				default:
					return this.domvariable.get(dir);
			}
		}
		return new Domain();
	}
	
	
	public int getDomainNumber(Domain Domain) {
		return this.domvariable.indexOf(Domain);
	}
	
	public List<Domain> getdomvariable() {
		return this.domvariable;
	}

	private OrderType parseOrderType(String orderType) {
		if (orderType.toLowerCase().equals("static")) { 
			return OrderType.STATIC; 
		}
		else if (orderType.toLowerCase().equals("dynamic")) {
			return OrderType.DYNAMIC;
		}
		return OrderType.DEFAULT;
	}
	
	/** * ********************************************************************************************************************************
	Calling Constraints 	 
	/** * ********************************************************************************************************************************/

	public SolvingConstraints getConstraint(int dir1, int dir2) {
		for (SolvingConstraints constraint : this.constraints) {
			if ((constraint.getVariables().getfirst() == dir1 && constraint.getVariables().getSecond() == dir2)
					|| (constraint.getVariables().getfirst() == dir2 && constraint.getVariables().getSecond() == dir1)) {
				return constraint;
			}
		}
		return null;
	}
	
	public List<SolvingConstraints> getConstraintsFor(int Domain) {
		List<SolvingConstraints> filteredConstraints = new ArrayList<SolvingConstraints>();
		for (SolvingConstraints constraint : this.constraints) {
			if (constraint.getVariables().getfirst() == Domain || constraint.getVariables().getSecond() == Domain) {
				filteredConstraints.add(constraint);
			}
		}
		return filteredConstraints;
	}
	
		
	public int getNumberOfdomvariable() {
		return this.domvariable.size();
	}
	
	private List<Domain> parsedomvariable(int[][] DomainBounds) {
		List<Domain> domvariable = new ArrayList<Domain>();
		for (int i = 0; i < DomainBounds.length; i++) {
			Domain Domain = new Domain();
			for (int j = DomainBounds[i][0]; j <= DomainBounds[i][1]; j++) {
				Domain.increaseValue(j);
			}
			domvariable.add(Domain);
		}
		return domvariable;
	}
	
	private List<SolvingConstraints> parseConstraints(ArrayList<?> constraintval) {
		List<SolvingConstraints> constraints = new ArrayList<SolvingConstraints>();
		for (int i = 0; i < constraintval.size(); i += 2) {
			int[] constraintVariables = (int[])constraintval.get(i) ;
			SolvingConstraints constraint = new SolvingConstraints(constraintVariables[0], constraintVariables[1]);
			for (Object val : (ArrayList<?>)constraintval.get(i+1)) {
				int[] tuple = (int[])val;
				constraint.addConstraint(tuple[0], tuple[1]);
			}
			constraints.add(constraint);
		  }
		return constraints;
	}
	
	
	private List<Domain> copyDomainvariable(List<Domain> original) {
		List<Domain> domvariable = new ArrayList<Domain>();
		for (Domain Domain : original) {
			domvariable.add(Domain.clone());
		}
		return domvariable;
	}
	
	private List<Domain> copyAndRemove(List<Domain> original, int minimalSize, List<Tuple<Integer,Integer>> result) 
	{
		List<Domain> domvariable = new ArrayList<Domain>();
		for (int i = 0; i < original.size(); i++) 
		{
			while (original.get(i).getval().size() >= minimalSize) 
			{
				boolean fin = false;
				
		for (Tuple<Integer, Integer> sol : result) {
					while (sol.getfirst() == i) {
						fin = true;
					}
				}
				if (!fin) {
					domvariable.add(original.get(i));
				}
			}
		}
		return domvariable;
	}
}