package code;

import java.util.Comparator;

public class AstarComparator implements Comparator<Node> {
	public int compare(Node s1, Node s2) {
		if(s1.getHeuristic()+s1.getTotalCost() >  s2.getHeuristic()+s2.getTotalCost())
			return 1;
		if(s1.getHeuristic()+s1.getTotalCost() <  s2.getHeuristic()+s2.getTotalCost())
			return -1;
		return 0;
		
}
}
