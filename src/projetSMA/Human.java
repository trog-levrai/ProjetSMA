package projetSMA; 

import bsh.Console;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Human extends Agent {
	
	public Human(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, House house) {
		super(pos, grid);
		this.job = job;
		this.house = house;
		this.destination = house;
	}

	@Override
	public void step() {
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = this.pos.getLocation(this.destination);
		double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
		pos.moveByVector(this, 2, angle, 0);
		myPoint = pos.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
		if (this.pos.getDistance(myPoint, otherPoint) <= 1.5) {
			changeDist();
		}
	}
	
	private void changeDist() {
		if (this.destination == this.house) {
			this.destination = this.job;
		} else {
			this.destination = this.house;
		}
	}
	
	public Place getJob() {
		return job;
	}
	
	public House getHouse() {
		return this.house;
	}
	
	protected Place destination;
	protected Place job;
	protected int a = 1;
	protected House house;
}