package projetSMA;

import java.util.List;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Bus extends Transport {

	public Bus(ContinuousSpace<Object> pos, Grid<Object> grid, int speed, int capacity, List<Place> stops) {
		super(pos, grid, speed, capacity);
		this.stops = stops;
		this.curr_dest = stops.get(curr_idx);
	}
	
	@Override
	public void step() {
		NdPoint myPoint  = pos.getLocation(this);
		NdPoint otherPoint = this.pos.getLocation(this.curr_dest);

		if (curr_stand_by == 0)
		{
			double angle = SpatialMath.calcAngleFor2DMovement(pos, myPoint, otherPoint);
			pos.moveByVector(this, 2, angle, 0);
			myPoint = pos.getLocation(this);
			grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
		}
		
		if (this.pos.getDistance(myPoint, otherPoint) <= 1.0 && curr_stand_by++ == stand_by)
			changeDist();
	}

	private void changeDist() {
		this.curr_stand_by = 0;
		if (this.curr_idx >= this.stops.size())
			this.curr_idx = 0;
		this.curr_dest = stops.get(this.curr_idx++);
	}
	
	public List<Place> getStops() {
		return this.stops;
	}
	
	private List<Place> stops;
	private int curr_idx = 0;
	private Place curr_dest;
	private int stand_by = 10;
	private int curr_stand_by = 0;
}