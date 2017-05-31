package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class School extends Place {

	public School(ContinuousSpace<Object> pos, Grid<Object> grid, int capacity, int time) {
		super(pos, grid, capacity, time);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getName() {
		return "School";
	}

}
