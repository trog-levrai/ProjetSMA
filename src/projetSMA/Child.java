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
		this.destination = house;
		this.final_destination = house;
		this.isAtDestination = false;
		this.timeInDestination = 0;
		this.destination = timeLine.getDestination(this);
		// TODO Auto-generated constructor stub
	}
	
	public Adult getParent() {
		return this.parent;
	}
	
	@Override
	public void step() {
		//if (isAtDestination) {
		//	timeInDestination--;
		//	if (timeInDestination == 0)
		//		isAtDestination = false;
		//	return;
		//}
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint parentPoint = pos.getLocation(parent);
		NdPoint otherPoint = pos.getLocation(this.destination);
		//checkDestination(myPoint, otherPoint);
	}
	
	private void followParent() {
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint parentPoint = pos.getLocation(parent);
		double dist = this.pos.getDistance(myPoint, parentPoint);
		double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, parentPoint);
		pos.moveByVector(this, dist, angle, 0);
	}
	//private void checkDestination(NdPoint myPoint, NdPoint otherPoint) {
	//	if (!(this.pos.getDistance(myPoint, otherPoint) <= 1.0))
	//		return;
	//	this.isAtDestination = true;
	//	this.timeInDestination = destination.time;
	//	double dist = this.pos.getDistance(myPoint, otherPoint);
	//	double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
	//	pos.moveByVector(this, dist, angle, 0);
	//}

	private Adult parent;
	protected Place isInPlace;
	protected boolean isWaiting;
	protected boolean isFollowing;
}