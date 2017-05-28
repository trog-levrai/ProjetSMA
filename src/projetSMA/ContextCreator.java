package projetSMA;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;


public class ContextCreator implements ContextBuilder<Object> {
	
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("ProjetSMA");

		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("SMA network", context, true);
		netBuilder.buildNetwork();

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		GridBuilderParameters<Object> gbp = new GridBuilderParameters<Object>(new WrapAroundBorders(), new SimpleGridAdder<Object>(), false, 50, 50);
		Grid<Object> grid = gridFactory.createGrid("grid", context, gbp);
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,
				new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(), 50, 50);

		for(int i = 0; i < 100; i++) {
			House house = new House(space, grid, 1, 1);
			context.add(house);
			Office job = new Office(space, grid, 1, 1);
			context.add(job);
			context.add(new Human(space, grid, job, house));	
		}
		
		for (Object obj : context)
		{
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int)pt.getX(), (int)pt.getY());
		}
		
		return context;
	}
}