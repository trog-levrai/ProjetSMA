package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Transport extends Agent {

	public Transport(ContinuousSpace pos, Grid<Agent> grid, int speed, int capacity) {
		super(pos, grid);
		this.speed = speed;
		this.capacity = capacity;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	protected int capacity;
	protected int speed;

}
