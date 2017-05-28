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
	
	private List<ArrayList<Place>> buses;
	public Human(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, House house, List<ArrayList<Place>> stations_per_lines) {
		super(pos, grid);
		this.job = job;
		this.house = house;
		this.destination = house;
		this.isAtDestination = false;
		this.timeInDestination = 0;
		this.buses = stations_per_lines;
	}

	@Override
	public void step() {
		if (isAtDestination) {
			if (timeInDestination == 0) {
				isAtDestination = false;
				changeDist();
			}
			timeInDestination--;
			return;
		}
		
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = this.pos.getLocation(this.destination);
		double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
		pos.moveByVector(this, 2, angle, 0);
		myPoint = pos.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());

		checkDestination(myPoint, otherPoint);
	}
	
	private void calc_best_course() {
		need_to_take_bus = false;
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = this.pos.getLocation(this.destination);
		double dist_walk = this.pos.getDistance(myPoint, otherPoint);
		
		double min_dist_station = Double.MAX_VALUE;

		int i = 0, j = 0;
		for (; i < buses.size(); i++)
			for (; j < buses.get(i).size(); j++) {
				NdPoint station = this.pos.getLocation(buses.get(i).get(j));
				double dist_to_station = this.pos.getDistance(myPoint, station);
				if (dist_to_station < min_dist_station) {
					min_dist_station = dist_to_station;
					this.nearest_station = buses.get(i).get(j);
				}
			}
		
		if (min_dist_station > dist_walk)
			return;

		min_dist_station = Double.MAX_VALUE;
		for (; j < buses.get(i).size(); j++) {
			NdPoint station = this.pos.getLocation(buses.get(i).get(j));
			double dist_to_station = this.pos.getDistance(station, otherPoint);
			if (dist_to_station < min_dist_station) {
				min_dist_station = dist_to_station;
				this.arrival_station = buses.get(i).get(j);
			}
		}
		
		NdPoint nearest_station_point = this.pos.getLocation(nearest_station);
		NdPoint arrival_station_point = this.pos.getLocation(arrival_station);
		
		// Sum all station + account for modulus
		double bus_course_dists = 0;
		List<Place> best_course;
		
		double dist_sum = this.pos.getDistance(myPoint, nearest_station_point) +
						  bus_course_dists +
						  this.pos.getDistance(arrival_station_point, otherPoint);
	}
	
	private void changeDist() {
		if (this.destination == this.house) {
			this.destination = this.job;
		} else {
			this.destination = this.house;
		}
	}
	
	private void checkDestination(NdPoint myPoint, NdPoint otherPoint) {
		if (!(this.pos.getDistance(myPoint, otherPoint) <= 1.0))
			return;
		this.isAtDestination = true;
		this.pos = destination.pos;
		this.timeInDestination = destination.time;
	}
	
	public Place getJob() {
		return job;
	}
	
	public House getHouse() {
		return this.house;
	}
	
	protected boolean need_to_take_bus = false;
	protected Place nearest_station;
	protected Place arrival_station;
	protected Place destination;
	protected Place job;
	protected House house;
	protected boolean isAtDestination;
	protected int timeInDestination;
	
}