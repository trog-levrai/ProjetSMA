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
		pos.moveByVector(this, 2, angle, 0);
		myPoint = pos.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());

		checkDestination(myPoint, otherPoint);
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
	
	protected Place destination;
	protected Place job;
	protected House house;
	protected boolean isAtDestination;
	protected int timeInDestination;
	Grid<Object> grid;
	
}