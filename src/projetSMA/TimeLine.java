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
			
		}
		if (day > 7)
			day = 1;
	}
	
	//public Object getDestination() {}
	
	Place getDestination(Human agent) {
		return agent.getDestination(this.time, this.day, this.ticPerDay, this.parks);
	}
	protected int time;
	protected int day;
	protected int ticPerDay;
	protected List<Park> parks;
}
