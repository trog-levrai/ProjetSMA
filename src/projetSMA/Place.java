package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Place extends Agent {

	public Place(ContinuousSpace<Object> pos, Grid<Object> grid, int capacity, int time) {
		super(pos, grid);
		this.time = time;
		this.capacity = capacity;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}
	
	public String getName() {
		return "Place";
	}

	public int getTime() {
		return this.time;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	protected int capacity;
	protected int time;
}
