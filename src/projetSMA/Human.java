package projetSMA; 

import bsh.Console;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Human extends Agent {
	
	public Human(ContinuousSpace<Object> pos, Grid<Object> grid, Place job) {
		super(pos, grid);
		this.job = job;
	}

	@Override
	public void step() {
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = new NdPoint(25, 25);
		double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
		pos.moveByVector(this, 2, angle, 0);
		myPoint = pos.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
	}
	
	public Place getJob() {
		return job;
	}
	
	protected Place job;
	protected int a = 1;
}
