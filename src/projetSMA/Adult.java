package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Adult extends Human {

	public Adult(ContinuousSpace pos, Grid<Agent> grid, Place job) {
		super(pos, grid, job);
		// TODO Auto-generated constructor stub
	}
	
	private void setChild(Child child) {
		this.child = child;
	}

	public Child getChild() {
		return this.child;
	}
	
	private Child child;
}
