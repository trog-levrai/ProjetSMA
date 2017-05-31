package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Office extends Place {

	public Office(ContinuousSpace<Object> pos, Grid<Object> grid, int capacity, int time) {
		super(pos, grid, capacity, time);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getName() {
		return "Office";
	}

}
