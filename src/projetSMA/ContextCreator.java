package projetSMA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

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

import java.util.ArrayList;
import java.util.List;

public class ContextCreator implements ContextBuilder<Object> {
	
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("ProjetSMA");
		int x = 50;
		int y = 50;
		int nb = 100;
		
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("SMA network", context, true);
		netBuilder.buildNetwork();

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		GridBuilderParameters<Object> gbp = new GridBuilderParameters<Object>(new WrapAroundBorders(), new SimpleGridAdder<Object>(), false, x, y);
		Grid<Object> grid = gridFactory.createGrid("grid", context, gbp);
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,
				new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.StrictBorders(), x, y);

		List<ArrayList<Place>> stations_per_lines = new ArrayList<ArrayList<Place>>();
		int nb_stations = 5;
		int nb_lines = 1;
		for (int j = 0; j < nb_lines; j++) {
			stations_per_lines.add(new ArrayList<Place>());
			for (int i = 0; i < nb_stations; i++) {
				Place station = new Place(space, grid, 1, 1);
				stations_per_lines.get(j).add(station);
				context.add(station);
			}
		}

		List<Bus> buses = new ArrayList<Bus>();
		for (int i = 0; i < 2; i++) {
			Bus bus = new Bus(space, grid, 4, 1, stations_per_lines.get(0));
			buses.add(bus);
			context.add(bus);
		}
		
		School school = new School(space, grid, 1000, 100);
		context.add(school);
		for(int i = 0; i < 10; i++) {
			House house = new House(space, grid, 1, 25);
			context.add(house);
			Office job = new Office(space, grid, 1, 15);
			context.add(job);
			Adult adult = new Adult(space, grid, job, house, stations_per_lines, buses);
			Child child = new Child(space, grid, school, adult, house, stations_per_lines, buses);
			context.add(adult);
			context.add(child);
		}
		
		for (Object obj : context)
		{
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int)pt.getX(), (int)pt.getY());
		}
		
		return context;
	}
}