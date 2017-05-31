package projetSMA;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class House extends Place {

	public House(ContinuousSpace<Object> pos, Grid<Object> grid, int capacity, int time) {
		super(pos, grid, capacity, time);
	}
	
	@Override
	public String getName() {
		return "House";
	}

}
