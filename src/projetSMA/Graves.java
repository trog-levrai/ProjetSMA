package projetSMA;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Graves extends Place{
	public Graves(ContinuousSpace<Object> pos, Grid<Object> grid, Context<Object> context, int capacity, int time, NdPoint housePoint) {
		super(pos, grid, capacity, time);
		context.add(this);
		pos.moveTo(this, housePoint.getX(), housePoint.getY());
	}
}
