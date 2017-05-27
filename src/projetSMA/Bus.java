package projetSMA;

import java.util.List;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Bus extends Transport {

	public Bus(ContinuousSpace<Object> pos, Grid<Object> grid, int speed, int capacity, List<ContinuousSpace<Object>> stops) {
		super(pos, grid, speed, capacity);
		this.stops = stops;
		// TODO Auto-generated constructor stub
	}
	
	public List<ContinuousSpace<Object>> getStops() {
		return this.stops;
	}
	
	private List<ContinuousSpace<Object>> stops;

}