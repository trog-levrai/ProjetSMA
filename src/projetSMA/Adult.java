package projetSMA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Adult extends Human {

	public Adult(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, House house, List<ArrayList<Place>> station_list, List<Bus> buses, TimeLine timeLine) {
		super(pos, grid, job, house, station_list, buses, timeLine);
		// TODO Auto-generated constructor stub
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public void setChild(Child child) {
		this.child = child;
	}
	
	@Override
	public void step() {
		//if (isAtDestination) {
		//	timeInDestination--;
		//	if (timeInDestination == 0) {
		//		isAtDestination = false;
		//		this.need_to_change_dist = true;
		//	}
		//	return;
		//}
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = this.pos.getLocation(this.destination);
		NdPoint finalPoint = this.pos.getLocation(this.final_destination);
		
		double angle;
		if (!inside_bus) {
			angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
			pos.moveByVector(this, 0.5, angle, 0);
		}
		else {
			NdPoint busPoint = this.pos.getLocation(this.nearest_bus);
			double dist = this.pos.getDistance(myPoint, busPoint);
			angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, busPoint);
			pos.moveByVector(this, dist, angle, 0);
		}

		myPoint = pos.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());

		if (!need_to_take_bus) {
			if (this.pos.getDistance(myPoint, finalPoint) <= 1.0)
				changeDist();
			calc_best_course();
		}
		else {
			NdPoint nearestStationPoint = this.pos.getLocation(this.nearest_station);
			NdPoint arrivalStationPoint = this.pos.getLocation(this.arrival_station);

			if (this.pos.getDistance(myPoint, nearestStationPoint) <= 1.0) {
				NdPoint nearestBusPoint = this.pos.getLocation(this.nearest_bus);
				if (this.pos.getDistance(myPoint, nearestBusPoint) <= 1.0)
					inside_bus = true;
			}
			else if (!inside_bus && this.destination == this.final_destination && this.pos.getDistance(myPoint, finalPoint) <= 1.0) {
				curr_bus_course_index = 0;
				need_to_take_bus = false;
				changeDist();
			}
			else if (inside_bus && this.pos.getDistance(myPoint, arrivalStationPoint) <= 2.0) {
				inside_bus = false;
				this.destination = this.final_destination;
			}
			else if (!inside_bus && this.pos.getDistance(myPoint, otherPoint) <= 1.0 && curr_bus_course_index < best_course.size()) {
				this.destination = best_course.get(curr_bus_course_index++);
			}
		}

		//checkDestination(myPoint, otherPoint);
		moveToDestinationWhenCloseToIt(myPoint, otherPoint);
		
		final int rand = new Random().nextInt(convert_terrorist_chance);
		if (rand < 1) {
			job = new Terrorist();
		}
	}
	
	public void moveToDestinationWhenCloseToIt(NdPoint myPoint, NdPoint otherPoint) {
		if (!(this.pos.getDistance(myPoint, otherPoint) <= 1.0)) {
			return;
		}
		double dist = this.pos.getDistance(myPoint, otherPoint);
		double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
		pos.moveByVector(this, dist, angle, 0);
		job.doJob(this);
	}

	public Child getChild() {
		return this.child;
	}
	
	private Child child;
	private Job job;
	private final int convert_terrorist_chance = 10000; // one of
}