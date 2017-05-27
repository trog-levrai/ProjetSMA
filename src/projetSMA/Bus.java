package projetSMA;

import java.util.List;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Bus extends Transport {

	public Bus(ContinuousSpace pos, Grid<Agent> grid, int speed, int capacity, List<ContinuousSpace> stops) {
		super(pos, grid, speed, capacity);
		this.stops = stops;
		// TODO Auto-generated constructor stub
	}
	
	public List<ContinuousSpace> getStops() {
		return this.stops;
	}
	
	private List<ContinuousSpace> stops;

}