package projetSMA;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Child extends Human {

	public Child(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, Adult parent, House house, List<ArrayList<Place>> station_list, List<Bus> buses, TimeLine timeLine) {
		super(pos, grid, job, house, station_list, buses, timeLine);
		this.parent = parent;
		this.isInPlace = house;
		this.job = job;
		this.house = house;
		this.isAtDestination = false;
		this.timeInDestination = 0;
		this.isWaiting = true;
		this.destination = timeLine.getDestination(this);
		this.isInPlace = house;
		this.isFollowing = false;
		// TODO Auto-generated constructor stub
	}
	
	public Adult getParent() {
		return this.parent;
	}
	
	@Override
	public void step() {
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint parentPoint = pos.getLocation(parent);
		NdPoint destinationPoint = pos.getLocation(this.destination);
		
		if (this.pos.getDistance(myPoint, destinationPoint) <= 1.0) {
			isInPlace = destination;
			destination = timeLine.getDestination(this);
			if (isInPlace != destination)
				isWaiting = true;
			else
				isWaiting = false;
			isFollowing = false;
			return;
		}
		
		if (isWaiting && (this.pos.getDistance(myPoint, parentPoint) <= 1.0) && !(parent.destination instanceof Office)) {
			isWaiting = false;
			isFollowing = true;
		}
		
		if (isFollowing)
			followParent();
	}
	
	private void followParent() {
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint parentPoint = pos.getLocation(parent);
		double dist = this.pos.getDistance(myPoint, parentPoint);
		double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, parentPoint);
		pos.moveByVector(this, dist, angle, 0);
	}

	private Adult parent;
	protected Place isInPlace;
	protected boolean isWaiting;
	protected boolean isFollowing;
}