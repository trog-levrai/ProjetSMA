package projetSMA; 

import java.util.ArrayList;
import java.util.List;

import bsh.Console;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class Human extends Agent {
	
	public Human(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, House house, List<ArrayList<Place>> stations_per_lines, List<Bus> bus_list, TimeLine timeLine) {
		super(pos, grid);
		this.job = job;
		this.house = house;
		this.destination = house;
		this.final_destination = house;
		this.isAtDestination = false;
		this.timeInDestination = 0;
		this.stations_list = stations_per_lines;
		this.bus_list = bus_list;
		this.best_course = new ArrayList<Place>();
		this.nearest_station = job;
		this.timeLine = timeLine;
	}
	
	@Override
	public void step() {
	}
	

	protected Place final_destination;
	protected boolean need_to_take_bus = false;
	protected boolean inside_bus = false;
	protected Bus nearest_bus;
	protected List<Place> best_course;
	protected int curr_bus_course_index = 0;
	protected Place nearest_station;
	protected Place arrival_station;
	protected Place destination;
	protected Place job;
	protected House house;
	protected boolean isAtDestination;
	protected int timeInDestination;
	protected List<ArrayList<Place>> stations_list;
	protected List<Bus> bus_list;
	protected TimeLine timeLine;
}