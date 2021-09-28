package code;

import java.util.*;

public class GenericSearchProcedure {
	Queue<Node> Queue=new LinkedList<Node>();
	String Qing;
	public Integer expandedCount = 1;
	HashSet<State> expanded = new HashSet<State>();
	Boolean bottomReached = false;
	GenericSearchProblem problem;
	int depth;

	public GenericSearchProcedure(GenericSearchProblem problem, String Strategy) {
		this.problem = problem;

		// return solve(problem.getInitialState().getState(), Strategy, false);

	}

	public Node solve(String grid, String Qing, Boolean Visualize) {
		this.Qing = Qing;
		expandedCount = 0;
		switch (Qing) {
		case "BF":
			Queue<Node> queue = new LinkedList<Node>();
			queue.add(problem.getInitialState());
			while (!queue.isEmpty()) {

				Node current = queue.remove();
				
				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				if (problem.goalTest(current)) {
					return current;
				}
				for (Operator op : Operators) {

					Node child = problem.getNextState(current, op);
					if (child != null) {
						queue.add(child);
						
					}
				}

			}
			break;
		case "DF":
			Stack<Node> stack = new Stack<Node>();
			stack.add(problem.getInitialState());
			expanded.add(problem.getInitialState().getState());
			while (!stack.isEmpty()) {
				Node current = stack.pop();

				if (problem.goalTest(current)) {
					return current;
				}

				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();

				ArrayList<Node> children = new ArrayList<Node>();
				for (Operator op : Operators) {
					children.add(problem.getNextState(current, op));
				}
				for (Node child : children) {
					if (child != null) {
						stack.add(child);
					}
				}

			}
			break;
		case "UC":
			PriorityQueue<Node> Pqueue = new PriorityQueue<Node>(new UniformComparator());
			Pqueue.add(problem.getInitialState());
			while (!Pqueue.isEmpty()) {

				Node current = Pqueue.remove();
				
				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				if (problem.goalTest(current)) {
					return current;
				}
				for (Operator op : Operators) {

					Node child = problem.getNextState(current, op);
					if (child != null) {
						Pqueue.add(child);
					}
				}

			}
			break;
		case "GR1":
			PriorityQueue<Node> Pqueue1 = new PriorityQueue<Node>(new GreedyComparator());
			Pqueue1.add(problem.getInitialState());
			while (!Pqueue1.isEmpty()) {

				Node current = Pqueue1.remove();
				
				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				if (problem.goalTest(current)) {
					return current;
				}
				for (Operator op : Operators) {

					Node child = problem.getNextState(current, op);
					if (child != null) {
						Pqueue1.add(child);
					}
				}

			}
			break;
		case "GR2":
			PriorityQueue<Node> Pqueue2 = new PriorityQueue<Node>(new GreedyComparator());
			Pqueue2.add(problem.getInitialState());
			while (!Pqueue2.isEmpty()) {

				Node current = Pqueue2.remove();
				
				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				if (problem.goalTest(current)) {
					return current;
				}
				for (Operator op : Operators) {

					Node child = problem.getNextState(current, op);
					if (child != null) {
						Pqueue2.add(child);
					}
				}

			}
			break;
		case "AS1":
			PriorityQueue<Node> Pqueue3 = new PriorityQueue<Node>(new AstarComparator());
			Pqueue3.add(problem.getInitialState());
			while (!Pqueue3.isEmpty()) {

				Node current = Pqueue3.remove();
				
				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				if (problem.goalTest(current)) {
					return current;
				}
				for (Operator op : Operators) {

					Node child = problem.getNextState(current, op);
					if (child != null) {
						Pqueue3.add(child);
					}
				}

			}
			break;
		case "AS2":
			PriorityQueue<Node> Pqueue4 = new PriorityQueue<Node>(new AstarComparator());
			Pqueue4.add(problem.getInitialState());
			while (!Pqueue4.isEmpty()) {

				Node current = Pqueue4.remove();
				
				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				if (problem.goalTest(current)) {
					return current;
				}
				for (Operator op : Operators) {

					Node child = problem.getNextState(current, op);
					if (child != null) {
						Pqueue4.add(child);
					}
				}

			}
			break;
		}
		return null;
	}

}