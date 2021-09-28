package code;

import java.util.*;
import java.util.Map.Entry;

import code.*;
import tests.*;

public class Olympics extends GenericSearchProblem {
	//jarvis location Jx,Jy, flame location fx, fy
	static int jx,jy,fx,fy,row,column,totalComponents;
	static HashSet<String> ComponentsString=new HashSet<String>();
	static HashSet<String> PoisonousesString=new HashSet<String>();
	//record of reachable states
	static HashSet<String> allStates=new HashSet<String>();
	static Node initialState;
	static String Strategy;
	
	public static String genGrid() {
		double rand = Math.random()*10+5;int gridDimW =(int)rand ;
		rand = Math.random()*10+5;int gridDimH =(int)rand ;
		rand = Math.random()*23+2;int poison = (int) rand;
		rand = Math.random()*5+5;int components = (int) rand;
		rand = Math.random()*gridDimW;int jarvisY = (int) rand;
		rand = Math.random()*gridDimH;int jarvisX = (int) rand;
		rand = Math.random()*gridDimW;int flameY = (int) rand;
		rand = Math.random()*gridDimH;int flameX = (int) rand;
		System.out.println(gridDimW+"    "+ gridDimH+ "     " + components+"     " + poison+ "     " );
		int count =0;
		int i =0;
		String poisoncells="";
		int [] poisonousCells = new int [poison*2];
		while(count<poison) {
			rand = Math.random()*gridDimW;int poisonY = (int) rand;
			rand = Math.random()*gridDimH;int poisonX = (int) rand;
			boolean valid = true;
			for(int j=0;j<poisonousCells.length;j+=2) {
				if(poisonousCells[j]==poisonX &&poisonousCells[j+1]==poisonY ){
					valid = false;}}
			if((jarvisX==poisonX && jarvisY==poisonY) || (flameX==poisonX && flameY==poisonY))valid =false;
			if(valid) {
				count +=1;
				if(count>1)poisoncells+=",";
				poisoncells+=poisonX+","+poisonY;
				poisonousCells[i]=poisonX;poisonousCells[i+1]=poisonY;
				i+=2;
			}
		}
		 count =0;
		 i =0;
		String compCells="";
		int [] componentCells = new int [components*2];
		while(count<components) {
			rand = Math.random()*gridDimW;int compY = (int) rand;
			rand = Math.random()*gridDimH;int compX = (int) rand;
			boolean valid = true;
			for(int j=0;j<componentCells.length;j+=2) {
				if(componentCells[j]==compX &&componentCells[j+1]==compY ){
					valid = false;}}
			for(int j=0;j<poisonousCells.length;j+=2) {
				if(poisonousCells[j]==compX &&poisonousCells[j+1]==compY ){
					valid = false;}}
			if((jarvisX==compX && jarvisY==compY) || (flameX==compX && flameY==compY))valid =false;
			if(valid) {
				count +=1;
				if(count>1)compCells+=",";
				compCells+=compX+","+compY;
				componentCells[i]=compX;componentCells[i+1]=compY;
				i+=2;
			}
		}
		
		
		String grid =gridDimW+","+gridDimH+";"+jarvisY+","+jarvisX+";"+flameY+","+flameX+";"+compCells+";"+poisoncells;
		System.out.println(grid);
		return grid;
	}
	
	@Override
	public Node getInitialState() {
		return initialState;
	}

	@Override
	public boolean goalTest(Node node) {
		Boolean goal = true;
		if (node.getOperator() == Operator.LIGHT) {
			return goal;
		}
		return false;
	}
	
	
	public static void decode(String grid) {
		//assign values from input string for getting location of jarvis, flame, components, and poisonus
		ArrayList<Operator> operator=new ArrayList<Operator>();
		String[] split=grid.split(";");
		String[] gridDims=split[0].split(",");
		int[] gridDim=new int[gridDims.length];
		for(int i=0;i<gridDims.length;i++){
			gridDim[i]=Integer.parseInt(gridDims[i]);
		}
		row=gridDim[0];column=gridDim[1];
		
		//get (jarvis) dims from stirng
		String[] jarvis=split[1].split(",");
		jx=Integer.parseInt(jarvis[0]);
		jy=Integer.parseInt(jarvis[1]);
		HashSet<String> c=new HashSet<String>();
		State init=new State(jx,jy,c);
		initialState=new Node(init, null, null, 0, 0);
		initialState.setPathCost(0);
		allStates.add(jx+""+jy+""+c.toString());
		// get (poison) dimensions as string from the string 
		String[] poisons=split[4].split(",");
    
		//get (poisons) dimensions in int
		int[] poison=new int[poisons.length];
		for(int i=0;i<poisons.length;i=i+2){
			Poisonous p= new Poisonous(Integer.parseInt(poisons[i]), Integer.parseInt(poisons[i+1]));
			PoisonousesString.add(p.x+","+p.y);
		}
	      
	      
	   // get (components) dimensions as string from the string 
	   String[] comps=split[3].split(",");
	    
	   //get (components) dimensions in int
	   int[] components=new int[comps.length];
	   for(int i=0;i<comps.length;i=i+2){
		   Component p= new Component(Integer.parseInt(comps[i]), Integer.parseInt(comps[i+1]));
		   ComponentsString.add(p.x+","+p.y);
	   }
	      
	   //get (flame) from string
	   String[] flame=split[2].split(",");
	   fx=Integer.parseInt(flame[0]);
	   fy=Integer.parseInt(flame[1]);
	   initialState.setHeuristic(getNearestComp1(jx, jy, ComponentsString));
	}
	@Override
	public ArrayList<Operator> getAllowedOperators(Node state) {
		//get current location from state node
		int x=state.getState().x;
		int y=state.getState().y;
		//get the collected components from state node
		HashSet<String> collected=(HashSet<String>) state.getState().collected.clone();
		ArrayList<Operator> operators=new ArrayList<Operator>();
		//in up,down,right,left we check first if the next location will be poisonus or not so jarvis won't step on poisonus
		if(!PoisonousesString.contains(x+","+(y-1)) && y>0) operators.add(Operator.LEFT);
		if(!PoisonousesString.contains((x-1)+","+(y)) && x>0) operators.add(Operator.UP);
		if(!PoisonousesString.contains(x+","+(y+1)) && y<(column-1)) operators.add(Operator.RIGHT);
		if(!PoisonousesString.contains((x+1)+","+(y)) && x<(row-1)) operators.add(Operator.DOWN);
		//if there's component in next location and it's not existed in collected components list then we can pick it
		if(ComponentsString.contains(x+","+y) && !state.getState().getCollected().contains(x+","+y)) operators.add(Operator.PICK);
		//if jarvis collected all the components and the next location has the flame, flame can be applicable
		if(fx==x && fy==y && collected.size()==ComponentsString.size()) operators.add(Operator.LIGHT);
		return operators;
	}
	
	@Override
	public Node getNextState(Node previousNode, Operator action) {
		// returns the successor node in this method
		
		//get current location from state node
		int x=previousNode.getState().getX();
		int y=previousNode.getState().getY();
		HashSet<String> collected= (HashSet<String>) previousNode.getState().getCollected().clone();//get the collected components from state node
		int depth=previousNode.getDepth() + 1; //increment depth
		switch(action) {
		case UP:x--;break;
		case DOWN:x++;break;
		case LEFT:y--;break;
		case RIGHT:y++;break;
		case PICK: 
			// add component in collected list
			Component comp=new Component(x,y);
			collected.add(x+","+y);
			State nxtState = new State(x,y,collected);
			Node next = new Node(nxtState, previousNode, action, depth, 0);
			next.setPathCost(1); //set the path cost & add it with parent's total cost and assign it to current's total cost 
			
			// set the manhatten heurestic value
			if(Strategy.equals("GR1") ||Strategy.equals("AS1")) {
				HashSet<String> s= (HashSet<String>) ComponentsString.clone();
				s.removeAll(collected);
				next.setHeuristic(getNearestComp1(x, y,s));
			}
			// set the euclidean heurestic value
			else if(Strategy.equals("GR2") ||Strategy.equals("AS2")) {
				HashSet<String> s1= (HashSet<String>) ComponentsString.clone();
				s1.removeAll(collected);
				next.setHeuristic(getNearestComp2(x, y,s1));break;
			}
			
			allStates.add(x+""+y+""+collected.toString());
			return next;
		case LIGHT:
			Node goal = new Node(previousNode.state, previousNode, action, depth, 0);
			goal.setPathCost(1);
			if(Strategy.equals("GR1") ||Strategy.equals("AS1")) { 
				HashSet<String> s= (HashSet<String>) ComponentsString.clone();
				s.removeAll(collected);
				goal.setHeuristic(getNearestComp1(x, y,s));
			}
			else if(Strategy.equals("GR2") ||Strategy.equals("AS2")) {
				HashSet<String> s1= (HashSet<String>) ComponentsString.clone();
				s1.removeAll(collected);
				goal.setHeuristic(getNearestComp2(x, y,s1));
			}
			return goal;
		}
		// in case any operators except pick and light they will have 10pt path cost
		State nxtState = new State(x,y,collected);
		
		// in case if it's repeated state then there're no nodes returned in this method
		if(checkRepeatedState(nxtState)) {
			return null;
		}
		Node next = new Node(nxtState, previousNode, action, depth, 0);
		next.setPathCost(10);
		if(Strategy.equals("GR1") ||Strategy.equals("AS1")) {
			HashSet<String> s= (HashSet<String>) ComponentsString.clone();
			s.removeAll(collected);
			next.setHeuristic(getNearestComp1(x, y,s));
		}
		else if(Strategy.equals("GR2") ||Strategy.equals("AS2")) { 
			HashSet<String> s1= (HashSet<String>) ComponentsString.clone();
			s1.removeAll(collected);
			next.setHeuristic(getNearestComp2(x, y,s1));
		}
		allStates.add(x+""+y+""+collected.toString());
		return next;
	}
	
	// returns boolean if it's repeated state or not
	public boolean checkRepeatedState(State p) {
		String res=p.x+""+p.y+""+p.collected.toString();
		if(allStates.contains(res)) {
			return true;
		}
		return false;
	}
	public static String solve(String grid, String strategy, boolean visualize) {
		//set new olympics
		Olympics oly = new Olympics();
		//set empty lists
		ComponentsString=new HashSet<String>();
		PoisonousesString=new HashSet<String>();
		allStates=new HashSet<String>();
		// set strategy value
		Strategy=strategy;
		//decode the input string
		oly.decode(grid);
		GenericSearchProcedure generic= new GenericSearchProcedure(oly, strategy);
		Node solution= generic.solve(grid, strategy, false);
		if(visualize==true)
			return show(solution)+";"+generic.expandedCount+"\r\n"+visualize(solution);
		return show(solution)+";"+generic.expandedCount;
	}
	
	// euclidean heurestic 
	public static int getNearestComp2(int x, int y,HashSet<String> s1) {
		int value=0;
		if(s1.isEmpty()) {
			int square=Math.abs((fx-x)*(fx-x))+Math.abs((fy-y)*(fy-y));
			double sqrt=Math.sqrt(square);
			value =(int) Math.round(sqrt);
			return value;
		}
		HashMap<Integer, String> nearest= new HashMap<Integer, String>();
		for(String component:s1) {
			String [] numbers=component.split(",");
			int x1=Integer.parseInt(numbers[0]);
			int y1=Integer.parseInt(numbers[1]);
			int square=((x-x1)*(x-x1))+((y-y1)*(y-y1));
			double sqrt=Math.sqrt(square);
			int res=(int) Math.round(sqrt);
			nearest.put(res, component);
		}
		Entry<Integer, String> entry = nearest.entrySet().iterator().next();
		value=entry.getKey();
		return value;
	}
	//manhattan heuristic
	public static int getNearestComp1(int x, int y,HashSet<String> s) {
		int value=0;
		HashMap<Integer, String> nearest= new HashMap<Integer, String>();
		if(s.isEmpty()) {
			value=Math.abs(fx-x)+Math.abs(fy-y);
			return value;
		}
		for(String component:s) {
			String [] numbers=component.split(",");
			int x1=Integer.parseInt(numbers[0]);
			int y1=Integer.parseInt(numbers[1]);
			int res=Math.abs(x-x1)+Math.abs(y-y1);
			nearest.put(res, component);
		}
		Entry<Integer, String> entry = nearest.entrySet().iterator().next();
		value=entry.getKey();
		return value;
	}
	// show sequence of operators that leads to the goal test from solution node
	public static String show(Node s) {
		Operator res=s.getOperator();
		if(s.parent.depth==0)
			return res.toString().toLowerCase();
		else
			return show(s.parent)+","+res.toString().toLowerCase();
	}
	
	// show all states by using solution node
	public static String visualize(Node s) {
		State state=s.getState();
		String res= "Node depth: "+s.depth+" | X: "+state.x +" | Y: "+state.y+ " | Collected_Components: "+ state.collected+ " | Total Cost:  "+ s.totalCost+" | Heuristic:  "+ s.heuristic+" | Action: "+ s.getOperator();
		if(s.parent==null) {
			return res;
		}
		else {
			return visualize(s.parent)+"\r\n"+res;
		}
	}
	
	public static void main(String[] args) {
		String grid13 = "15,15;5,1;14,1;3,14,11,11,12,12,4,8,8,14,5,0,13,6,0,6,5,11,11,0,7,0,12,11,6,2;6,1,14,10,6,5,6,11,4,2,9,1,2,12,2,6,1,7,2,7,2,3,2,5,8,11,4,3,2,0,5,10";
		String solution = Olympics.solve(grid13, "AS2", false);
		System.out.println(solution);
	}

	
	
}
