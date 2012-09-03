package cs4402.ConstraintProgrammingP1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**********************************************************************************************************************************
 * This is the Main Class. 
 * Here we instantiate both the algorithm classes. 
 * @author 110021201
 *********************************************************************************************************************************/


public class Main {

	
/**********************************************************************************************************************************
 *	Here we have create a new object for the Backtracking Algorithm and initializes it.  
 *  @param bta for Backtracking Algorithm
 *  @param int
 *  @return Execution time  
 * * 
 * @throws IOException 
 * ********************************************************************************************************************************/		

	public static void main(String[] args) throws IOException 
	{
		
		CSPReader cspreader = new CSPReader();
		CSP csp = null;
		//CSP csp = cspreader.read("4Queens.csp");
		//CSP csp = cspreader.read("6Queens.csp");
		
	    String choicemenu;
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("1. 4-Queen ");
	    System.out.println("2. 6-Queen ");
	    System.out.println();
	    System.out.println("Enter your choice(1/2):");
	    System.out.flush();
	    choicemenu = in.readLine();
	    int choice = Integer.parseInt(choicemenu);
	    
	    switch(choice)
	    {
	    case 1:{
	    csp = cspreader.read("4Queens.csp");
	    }
	    break;
	    
	    case 2:{
	    csp = cspreader.read("6Queens.csp");
	    }
	    break;
	    
	    default: System.out.println("Invalid Choice!!");
	    }
	    
		
		
		TheBackTrackingAlgorithm bta = new TheBackTrackingAlgorithm(csp);
		
		int initialize = System.identityHashCode(bta);
		bta.resolve();
		int terminate = System.identityHashCode(bta);
		
		bta.printSolution();
		System.out.println("The Execution time was "+ (terminate-initialize)+" ms.");
		
		csp.resettype();

/** * ********************************************************************************************************************************
 *	Here we have create a new object for the ForwardChecking Algorithm and initializes it.  
 *  @param fca for ForwardCheckingAlgorithm
 *  @param int
 *  @return Execution time  
 * * ********************************************************************************************************************************/		

		TheForwardCheckingAlgorithm fca = new TheForwardCheckingAlgorithm(csp);
		initialize = System.identityHashCode(fca);
		fca.resolve();
	
		terminate = System.identityHashCode(fca);
		fca.printSolution() ;
		System.out.println("The Execution time was "+(terminate-initialize)+" ms.");
		
		
	}
	}


