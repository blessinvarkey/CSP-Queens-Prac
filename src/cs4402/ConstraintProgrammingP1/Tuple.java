package cs4402.ConstraintProgrammingP1;

/** * ********************************************************************************************************************************
 * This Class contains the paired variables. 
 * @author 110021201
 * * ********************************************************************************************************************************/

public class Tuple<V1,V2> 
{

	private V1 first;
	private V2 second;
	
	public Tuple(V1 first, V2 second) 
	{
		this.first = first;
		this.second = second;
	}
	
	public V1 getfirst() {
		return this.first;
	}
	
	public V2 getSecond() {
		return this.second;
	}
}
