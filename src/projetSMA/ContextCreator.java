package projetSMA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import java.util.Random;
import java.util.stream.Stream;

public class ContextCreator implements ContextBuilder<Object> {
	
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("ProjetSMA");
		int nb = 100;
		
		try {
			Stream<String> stream = Files.lines(Paths.get("tests/foo.sma"));
			stream.forEach(s -> {
				this.x = s.length();
				this.y++;
				for (int i = 0; i < s.length(); i++) {
					switch (s.charAt(i)) {
						case 'H':
							this.house++;
							break;
						case 'O':
							this.office++;
							break;
						case 'S':
							this.school++;
							break;
						case 'B':
							this.bus++;
							break;
						case 'P':
							this.park++;
							break;
					}
				}
			});
			stream.close();
		} catch (Exception e) {
			this.x = 50;
			this.y = 50;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("SMA network", context, true);
		netBuilder.buildNetwork();

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		GridBuilderParameters<Object> gbp = new GridBuilderParameters<Object>(new WrapAroundBorders(), new SimpleGridAdder<Object>(), false, this.x, this.y);
		Grid<Object> grid = gridFactory.createGrid("grid", context, gbp);
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,
				new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.StrictBorders(), this.x, this.y);

		List<ArrayList<Place>> stations_per_lines = new ArrayList<ArrayList<Place>>();
		int nb_stations = this.bus;
		int nb_lines = 1;
		for (int j = 0; j < nb_lines; j++) {
			stations_per_lines.add(new ArrayList<Place>());
			for (int i = 0; i < nb_stations; i++) {
				Place station = new Place(space, grid, 1, 1);
				stations_per_lines.get(j).add(station);
				context.add(station);
			}
		}

		for (int i = 0; i < 1; i++) {
			Bus bus = new Bus(space, grid, 4, 1, stations_per_lines.get(i));
			context.add(bus);
		}
		
		List<School> s = new ArrayList<School>();
		for (int i = 0; i < this.school; ++i)
			s.add(new School(space, grid, 1000, 100));getClass();
		
		List<Office> o = new ArrayList<Office>();
		for (int i = 0; i < this.office; ++i)
			o.add(new Office(space, grid, 1000, 100));
		
		o.forEach(off -> context.add(off));
		s.forEach(sch -> context.add(sch));
		
		Random rand = new Random();
		for(int i = 0; i < this.house; i++) {
			House house = new House(space, grid, 1, 25);
			context.add(house);
			Adult adult = new Adult(space, grid, o.get(rand.nextInt(o.size())), house, stations_per_lines);
			Child child = new Child(space, grid, s.get(rand.nextInt(s.size())), adult, house, stations_per_lines);
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
	public int x = 0;
	public int y = 0;
	public int house = 0;
	public int office = 0;
	public int school = 0;
	public int park = 0;
	public int bus = 0;
}