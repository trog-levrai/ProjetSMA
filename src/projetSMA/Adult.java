package projetSMA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Adult extends Human {

	public Adult(ContinuousSpace<Object> pos, Grid<Object> grid, Place job, House house, List<ArrayList<Place>> station_list, List<Bus> buses, TimeLine timeLine) {
		super(pos, grid, job, house, station_list, buses, timeLine);
		// TODO Auto-generated constructor stub
	}
	
	public void setChild(Child child) {
		this.child = child;
	}

	public Child getChild() {
		return this.child;
	}
	
	public Place getDestination(int time, int day, int ticPerDay, List<Park> parks) {
		if (this.child.isWaiting)
			return this.child.isInPlace;
		if (this.child.isFollowing)
			return this.child.destination;
		if (time < ticPerDay / 2) {
			if (day > 5) {
				Random rand = new Random();
				return parks.get(rand.nextInt(parks.size()));
			}
			return this.job;
		}
		return this.house;
	}
	
	private Child child;
}