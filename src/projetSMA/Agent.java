package projetSMA;

import java.util.List;

import cern.jet.random.Uniform;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public abstract class Agent {

	public Agent(ContinuousSpace pos, Grid<Agent> grid) {
		this.pos = pos;
		this.grid = grid;
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public abstract void step();

	public ContinuousSpace getPos() {
		return this.pos;
	}
	
	protected ContinuousSpace pos;
	protected Grid<Agent> grid;
}