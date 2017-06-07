package projetSMA;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class NuclearExplosion extends Agent {

	public NuclearExplosion(ContinuousSpace<Object> pos, Grid<Object> grid, Context<Object> context, int duration) {
		super(pos, grid);
		this.duration = duration;
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void step() {
		duration--;
		if (duration == 0) {
			context.remove(this);
		}
	}
	
	private int duration;
	Context<Object> context;
	
}
