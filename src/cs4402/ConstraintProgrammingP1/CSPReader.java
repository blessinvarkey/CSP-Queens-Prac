package cs4402.ConstraintProgrammingP1;


import java.io.* ;
import java.util.* ;
import java.util.Map.Entry;
/** * ********************************************************************************************************************************
 * This is the CSP Reader Class. 
 * The class was provided in studres and is from the same  
 * @author 110021201
 * * * ********************************************************************************************************************************/


// A reader tailored for CSPs.
// It is created from a FileReader and a StreamTokenizer

// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
// File format:
// <no. vars>
// Vars indexed from 0
// We assume that the domain of all vars is specified as lb..ub
// <lb>, <ub>* (one per var)
// n-ary constraints (assume allowed tuples for now).
// c(<varno>, <varno> [,<varno>]*)
// tuples of the appropriate arity of the form: int, int, int...
// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

public final class CSPReader {
	FileReader inFR ;
	StreamTokenizer in ;

	/* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     main (for testing)
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% */
	/* public static void main(String[] args) {
    CSPReader reader = new CSPReader() ;
	reader.read(args[0]) ;
  } */

	public final CSP read(String fn) 
	{
		try {
			inFR = new FileReader(fn) ;								// Creates a new FileReader with the given file
			in = new StreamTokenizer(inFR) ;							
			in.ordinaryChar('(');									// Specifies the character is ordinary in the token 
			in.ordinaryChar(')') ;									//  
			in.nextToken() ;                                        // n
			
			int n = (int)in.nval ;								    // 
			System.out.println( "Here we have " + n +" vars \n" ) ; // Testing

			int[][] domainBounds = new int[n][2] ;
			for (int i = 0; i < n; i++) {
				in.nextToken() ;                                  	// 
				domainBounds[i][0] = (int)in.nval ;
				in.nextToken() ;                                   	// ','
				in.nextToken() ;
				domainBounds[i][1] = (int)in.nval ;				 	// Testing
				System.out.println( domainBounds[i][0]+ " .. " + domainBounds[i][1]) ;
			}
			in.nextToken();
			String orderType = in.sval;
			Map<Integer,Integer> O = readOrder();
			ArrayList<Object> C = readConstraints();
			inFR.close() ;
			return new CSP(domainBounds, C, O, orderType);
		}
		catch (FileNotFoundException e) {System.out.println(e);}
		catch (IOException e) {System.out.println(e);}
		return null;
	}

	/* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
     readConstraints
	 NB binary constraints assumed.
	 Returns an AList alternating entries: int[] scope, AL tuples
	 SUGGESTION: create a Constraint class to hold this.
     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% */
	private ArrayList<Object> readConstraints() 
	{
		ArrayList<Object> C = new ArrayList<Object>() ;

		try {
			while(in.ttype != StreamTokenizer.TT_EOF) {
				// scope
				int[] scope = new int[2] ;
				in.nextToken() ;                                       //'('
				in.nextToken() ;                                       //var
				scope[0] = (int)in.nval ;
				in.nextToken() ;                                       //','
				in.nextToken() ;                                       //var		
				scope[1] = (int)in.nval ;
				in.nextToken() ;                                       //')'

				//tuples
				ArrayList<int[]> tuples = new ArrayList<int[]>() ;
				in.nextToken() ;              //1st allowed val of 1st tuple
				while (!"c".equals(in.sval) && (in.ttype != StreamTokenizer.TT_EOF)) {
					int[] tuple = new int[2] ;
					tuple[0] = (int)in.nval ;
					in.nextToken() ;                                   //','
					in.nextToken() ;                               //2nd val
					tuple[1] = (int)in.nval ;
					tuples.add(tuple) ;
					in.nextToken() ;      //1stallowed val of next tuple/c/EOF
				}

				C.add(scope) ;
				C.add(tuples) ;
			}

		return C ;
		}
		catch (IOException e) {System.out.println(e);}
		return null ;  
	}

	private Map<Integer,Integer> readOrder() {

		Map<Integer,Integer> order = new HashMap<Integer,Integer>() ;
		try {
			int i = 0;
			in.nextToken() ; 
			while(in.ttype != StreamTokenizer.TT_EOF) {
				in.nextToken();
				if (in.sval != null && in.sval.equals("c")) {
					break;
				}
				in.nextToken();
				if (in.sval != null && in.sval.equals("c")) {
					break;
				}
				order.put(i, (int)in.nval);
				i++;
			}

/********************************************************************************************************************************
Testing
********************************************************************************************************************************/
			for (Entry<Integer,Integer> entry : order.entrySet()) {
				System.out.println("order(" + entry.getKey() + "," + entry.getValue() + ")");
			}

			return order ;
		}
		catch (IOException e) {System.out.println(e);}
		return null ;  
	}
}
