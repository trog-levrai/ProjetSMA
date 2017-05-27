package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Child extends Agent {

	public Child(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, Adult parent) {
		super(pos, grid);
		this.parent = parent;
		// TODO Auto-generated constructor stub
	}
	
	public Adult getParent() {
		return this.parent;
	}

	private Adult parent;

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}
}
