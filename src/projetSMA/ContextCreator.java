package projetSMA;

import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import cern.jet.random.Uniform;
import repast.simphony.*;

public class ContextCreator implements ContextBuilder<Agent> {
	
	@Override
	public Context<Agent> build(Context<Agent> context) {
		context.setId("ProjetSMA");
		int width = RunEnvironment.getInstance().getParameters().getInteger("gridWidth");
		int height = RunEnvironment.getInstance().getParameters().getInteger("gridHeight");
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		GridBuilderParameters<Agent> gbp = new GridBuilderParameters<Agent>(new WrapAroundBorders(), new SimpleGridAdder<Agent>(), false, width, height);
		Grid<Agent> grid = gridFactory.createGrid("grid", context, gbp);
		
		return context;
	}
}