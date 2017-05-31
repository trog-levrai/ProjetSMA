package projetSMA;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Adult extends Human {

	public Adult(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, House house, List<ArrayList<Place>> station_list, List<Bus> buses, TimeLine timeLine) {
		super(pos, grid, job, house, station_list, buses, timeLine);
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