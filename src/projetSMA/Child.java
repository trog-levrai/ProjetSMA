package projetSMA;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Child extends Human {

	public Child(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, Adult parent, House house, List<ArrayList<Place>> station_list, List<Bus> buses, TimeLine timeLine) {
		super(pos, grid, job, house, station_list, buses, timeLine);
		this.parent = parent;
		// TODO Auto-generated constructor stub
	}
	
	public Adult getParent() {
		return this.parent;
	}

	private Adult parent;
}