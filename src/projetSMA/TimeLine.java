package projetSMA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class TimeLine extends Agent{

	public TimeLine(ContinuousSpace<Object> pos, Grid<Object> grid, List<Park> parks, int ticPerDay) {
		super(pos, grid);
		// TODO Auto-generated constructor stub
		this.time = 0;
		this.day = 0;
		this.ticPerDay = ticPerDay;
		this.parks = parks;
	}

	@Override
	public void step() {
		time++;
		if (time > ticPerDay) {
			time = 0;
			day++;
			for (Adult a : adults) {
				Random rand = new Random();
				if (rand.nextFloat() * 100 <= a.deathChance)
					a.die();
			}
	
		}
		if (day > 7)
			day = 1;
	}
	
	//public Object getDestination() {}
	
	Place getDestination(Agent agent) {
		if (agent instanceof Adult) {
			Adult adult = (Adult) agent;
			if (adult.getChild().isWaiting)
				return adult.getChild().isInPlace;
			if (adult.getChild().isFollowing)
				return adult.getChild().destination;
			if (time < ticPerDay / 2) {
				if (day > 5) {
					Random rand = new Random();
					return parks.get(rand.nextInt(parks.size()));
				}
				return adult.job;
			}
			return adult.house;
		}
		else {
			Child child = (Child) agent;
			if (time < ticPerDay / 2) {
				if (day > 5) {
					Random rand = new Random();
					return parks.get(rand.nextInt(parks.size()));
				}
				return child.job;
			}
			return child.house;
		}
	}
	
	public void setAdultsList(List<Adult> adults) {
		this.adults = adults;
	}
	
	protected int time;
	protected int day;
	protected int ticPerDay;
	protected List<Park> parks;
	protected List<Adult> adults;
}
