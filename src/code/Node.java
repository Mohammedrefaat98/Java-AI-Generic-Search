package code;

public class Node {

State state; 
Node parent;
Operator operator;
Integer depth;
public Integer pathCost;
public Integer heuristic;
public Integer totalCost;

public Node(State state, Node parent, Operator operator, Integer depth, Integer pathCost) {
	super();
	this.state = state;
	this.parent = parent;
	this.operator = operator;
//	this.depth = (int) this.getPath().chars().filter(ch -> ch == ',').count()-1;
	this.depth=depth;
	this.pathCost = pathCost;
}
public String toString() {
	return parent+" "+operator+" "+depth+" "+pathCost+" "+ heuristic+ " "+ totalCost;
}
public State getState() {
	return state;
}

public void setState(State state) {
	this.state = state;
}
public Node getParent() {
	return parent;
}
public Operator getOperator() {
	return operator;
}
public Integer getDepth() {
	return depth;

}

public Integer getPathCost() {
	return this.pathCost;
}
public void setPathCost(Integer pathCost) {
	if(this.parent==null) {
		this.totalCost = pathCost;}
	else {
	this.totalCost = pathCost+this.getParent().getTotalCost();}
	this.pathCost = pathCost;

}

public Integer getHeuristic() {
	return heuristic;
}
public void setHeuristic(Integer heuristic) {
	this.heuristic = heuristic;
}
public int getTotalCost() {
	return totalCost;
}
public String getPath() {
	if (operator==null)
		return "";
	return parent.getPath() +operator+",";
}

}