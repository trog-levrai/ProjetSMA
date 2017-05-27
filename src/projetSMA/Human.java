package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Human extends Agent {
	
	public Human(ContinuousSpace pos, Grid<Agent> grid, Place job) {
		super(pos, grid);
		this.job = job;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}
	
	public Place getJob() {
		return job;
	}
	
	protected Place job;
}
