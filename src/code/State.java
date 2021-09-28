package code;

import java.util.HashSet;

public class State {
	int x;
	int y;
	HashSet<String> collected=new HashSet<String>();
	
	public State(int x, int y, HashSet<String> collected) {
		super();
		this.x = x;
		this.y = y;
		this.collected = collected;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public HashSet<String> getCollected() {
		return collected;
	}
	

}
