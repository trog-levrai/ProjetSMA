package projetSMA;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Graves extends Place {
	public Graves(ContinuousSpace<Object> pos, Grid<Object> grid, Context<Object> context, int capacity, int time, NdPoint housePoint, int repopTime, ContextCreator cb) {
		super(pos, grid, capacity, time);
		context.add(this);
		pos.moveTo(this, housePoint.getX(), housePoint.getY());
		this.repopTime = repopTime;
		this.pos = pos;
		this.context = context;
		this.cb = cb;
	}
	
	@Override
	public void step() {
		repopTime--;
		if (repopTime != 0)
			return;
		House house = new House(pos, grid, 1, 150);
		context.add(house);
		NdPoint pt = pos.getLocation(this);
		pos.moveTo(house, pt.getX(), pt.getY());
		context.remove(this);
		cb.addFamily(house);
	}
	
	protected int repopTime;
	protected ContinuousSpace<Object> pos;
	protected Context<Object> context;
	protected ContextCreator cb;
	
}
