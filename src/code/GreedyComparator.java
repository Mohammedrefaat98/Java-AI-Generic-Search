package code;

import java.util.Comparator;

public class GreedyComparator implements Comparator<Node> {
	public int compare(Node s1, Node s2) {
		if(s1.getHeuristic() >  s2.getHeuristic())
			return 1;
		if(s1.getHeuristic() <  s2.getHeuristic())
			return -1;
		return 0;
		
}
}
