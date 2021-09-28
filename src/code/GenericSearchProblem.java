package code;
import java.util.*;
public abstract  class GenericSearchProblem {
	
	public abstract Node getInitialState();

	public abstract boolean goalTest(Node node);

	public abstract ArrayList<Operator> getAllowedOperators(Node state);

	public abstract Node getNextState(Node previousNode, Operator action);
	
	
}