package projetSMA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import repast.simphony.context.Context;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Adult extends Human {

	public Adult(ContinuousSpace<Object> pos, Grid<Object> grid, Context<Object> context, Place job, House house, List<ArrayList<Place>> station_list, List<Bus> buses, TimeLine timeLine, float deathChance) {
		super(pos, grid, job, house, station_list, buses, timeLine);
		this.context = context;
		this.deathChance = deathChance;
	}
	
	@Override
	public void step() {
		
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

		moveToDestinationWhenCloseToIt(myPoint, otherPoint);
		final int rand = new Random().nextInt(convert_terrorist_chance);
		if (rand < 1) {
			job = new Terrorist();
		}
	}
	
	private void calc_best_course() {
		best_course.clear();
		need_to_take_bus = false;
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = this.pos.getLocation(this.final_destination);
		double dist_walk = this.pos.getDistance(myPoint, otherPoint);
		
		double min_dist_station = Double.MAX_VALUE;
		
		int nearest_station_index = 0, arrival_station_index = 0, nearest_line = 0;

		int i = 0, j = 0;
		for (; i < stations_list.size(); i++)
			for (; j < stations_list.get(i).size(); j++) {
				NdPoint station = this.pos.getLocation(stations_list.get(i).get(j));
				double dist_to_station = this.pos.getDistance(myPoint, station);
				if (dist_to_station < min_dist_station) {
					min_dist_station = dist_to_station;
					this.nearest_station = stations_list.get(i).get(j);
					nearest_station_index = j;
					nearest_line = i;
				}
			}
		
		if (min_dist_station > dist_walk)
			return;

		min_dist_station = Double.MAX_VALUE;
		for (j = 0; j < stations_list.get(nearest_line).size(); j++) {
			NdPoint station = this.pos.getLocation(stations_list.get(nearest_line).get(j));
			double dist_to_station = this.pos.getDistance(station, otherPoint);
			if (dist_to_station < min_dist_station) {
				min_dist_station = dist_to_station;
				this.arrival_station = stations_list.get(nearest_line).get(j);
				arrival_station_index = j;
			}
		}
		
		NdPoint nearest_station_point = this.pos.getLocation(nearest_station);
		NdPoint arrival_station_point = this.pos.getLocation(arrival_station);

		best_course.add(nearest_station);
		
		// Sum all station + account for modulus
		
		double bus_course_dists = 0;
		int st = 0;	
		for (st = nearest_station_index; st < arrival_station_index - 1; st++) {
			Place st_place = stations_list.get(nearest_line).get(st);
			NdPoint st_0 = this.pos.getLocation(st_place);
			NdPoint st_1 = this.pos.getLocation(stations_list.get(nearest_line).get(st + 1));
			bus_course_dists += this.pos.getDistance(st_0, st_1);

			//best_course.add(st_place);
		}

		best_course.add(stations_list.get(nearest_line).get(st));

		double nearest_bus_dist = Double.MAX_VALUE;
		for (int i1 = 0; i1 < bus_list.size(); i1++) {
			NdPoint bus = this.pos.getLocation(bus_list.get(i1));
			double bus_dist = this.pos.getDistance(nearest_station_point, bus);
			if (bus_dist < nearest_bus_dist) {
				nearest_bus_dist = bus_dist;
				nearest_bus = bus_list.get(i1);
			}
		}
		
		double dist_bus = nearest_bus_dist / 2 +
						  this.pos.getDistance(myPoint, nearest_station_point) +
						  bus_course_dists / 2 +
						  this.pos.getDistance(arrival_station_point, otherPoint);

		best_course.add(arrival_station);

		for (int i1 = 0; i1 < best_course.size(); i1++)
		
		if (dist_bus < dist_walk) {
			this.destination = nearest_station;
			need_to_take_bus = true;
		}
	}
	
	private void changeDist() {
		Place dist = timeLine.getDestination(this);
		this.destination = dist;
		this.final_destination = dist;
	}
	
	public void die() {
		destination = null;
		final_destination = null;
		context.remove(child);
		context.remove(house);
		context.remove(this);
	}
	
	public Place getJob() {
		return job_place;
	}
	
	public House getHouse() {
		return this.house;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public void setChild(Child child) {
		this.child = child;
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
	private Context<Object> context;
	protected float deathChance;
	private Job job;
	private final int convert_terrorist_chance = 50000; // one of
}