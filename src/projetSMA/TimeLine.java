package projetSMA;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class TimeLine extends Agent{

	public TimeLine(ContinuousSpace<Object> pos, Grid<Object> grid, int ticPerDay) {
		super(pos, grid);
		// TODO Auto-generated constructor stub
		this.time = 0;
		this.day = 0;
		this.ticPerDay = ticPerDay;
	}

	@Override
	public void step() {
		time++;
		if (time > ticPerDay)
			time = 0;
	}
	
	//public Object getDestination() {}
	
	Place getDestination(Agent agent) {
		if (agent instanceof Adult) {
			Adult adult = (Adult) agent;
			if (adult.getChild().isWaiting)
				return adult.getChild().isInPlace;
			if (adult.getChild().isFollowing)
				return adult.getChild().destination;
			if (time < ticPerDay / 2)
				return adult.job;
			return adult.house;
		}
		else {
			Child child = (Child) agent;
			if (time < ticPerDay / 2)
				return child.job;
			return child.house;
		}
	}
	protected int time;
	protected int day;
	protected int ticPerDay;
}
