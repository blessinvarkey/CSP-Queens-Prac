package cs4402.ConstraintProgrammingP1;

import java.util.ArrayList;
import java.util.List;

public class Domain  implements Cloneable, Comparable<Domain> {

	private List<Integer> val;
	
	public Domain() 
	{
		this.val = new ArrayList<Integer>();
	}
	
	public void increaseValue(int value) {
		this.val.add(value);
	}
	
	public void decreaseValue(int value) {
		this.val.remove(this.val.indexOf(value));
	}
	
	public int getFirst() {
		if(this.val.size() > 0) {
			return this.val.iterator().next();
		}
		return Integer.MIN_VALUE;
	}
	
	public List<Integer> getval() {
		return this.val;
	}
	
	@Override
	public Domain clone()
    {
		Domain domain = new Domain();
		for (Integer i : val) {
			domain.increaseValue(i);
		}
		return domain;
    }

	@Override
	public int compareTo(Domain o) {
		if (this.val.size() == o.val.size()) {
			return 0;
		}
		else if (this.val.size() > o.val.size()) {
			return 1;
		}
		return -1;
	} 
}
