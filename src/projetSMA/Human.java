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
		this.grid = grid;
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
		pos.moveByVector(this, 0.5, angle, 0);
		myPoint = pos.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());

		checkDestination();
	}
	
	private void changeDist() {
		if (this.destination == this.house) {
			this.destination = this.job;
		} else {
			this.destination = this.house;
		}
	}
	
	private void checkDestination() {
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = this.pos.getLocation(this.destination);
		double dist = this.pos.getDistance(myPoint, otherPoint);
		if (!(dist <= 0.6))
			return;
		this.isAtDestination = true;
		this.timeInDestination = destination.time;
		double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
		pos.moveByVector(this, dist, angle, 0);
	}
	
	public Place getJob() {
		return job;
	}
	
	public House getHouse() {
		return this.house;
	}
	
	protected Place destination;
	protected Place job;
	protected House house;
	protected boolean isAtDestination;
	protected int timeInDestination;
	
}