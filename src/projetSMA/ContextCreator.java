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
		this.context = context;
		context.setId("ProjetSMA");
		int nb = 100;
		String path = "tests/map2.sma";

		try {
			Stream<String> stream = Files.lines(Paths.get(path));
			stream.forEach(str -> {
				this.x = str.length();
				this.y++;
				for (int i = 0; i < str.length(); i++) {
					switch (str.charAt(i)) {
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
		this.grid = grid;
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,
				new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.StrictBorders(), this.x, this.y);
		this.space = space;

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
		this.stations_per_lines = stations_per_lines;

		List<Bus> buses = new ArrayList<Bus>();
		for (int i = 0; i < 2; i++) {
			Bus bus = new Bus(space, grid, 4, 1, stations_per_lines.get(0));
			buses.add(bus);
			context.add(bus);
		}
		this.buses = buses;
		
		List<School> s = new ArrayList<School>();
		List<Office> o = new ArrayList<Office>();
		List<House> h = new ArrayList<House>();
		List<Park> p = new ArrayList<Park>();
		List<Adult> a = new ArrayList<Adult>();
		List<Child> c = new ArrayList<Child>();
		
		for (int i = 0; i < this.school; ++i)
			s.add(new School(space, grid, 1000, 150));
		this.s = s;
		
		for (int i = 0; i < this.office; ++i)
			o.add(new Office(space, grid, 1000, 150));
		this.o = o;
		
		for (int i = 0; i < this.house; ++i)
			h.add(new House(space, grid, 1, 150));
		
		for (int i = 0; i < this.park; ++i)
			p.add(new Park(space, grid, 1, 150));
		o.forEach(off -> context.add(off));
		s.forEach(sch -> context.add(sch));
		h.forEach(hou -> context.add(hou));
		p.forEach(par -> context.add(par));
		
		TimeLine timeLine = new TimeLine(space, grid, p, 500);
		context.add(timeLine);
		this.timeLine = timeLine;
		
		Random rand = new Random();
		for(int i = 0; i < this.house; i++) {
			Adult adult = new Adult(space, grid, context, o.get(rand.nextInt(o.size())), h.get(i), stations_per_lines, buses, timeLine, 0.5f, this);
			Child child = new Child(space, grid, s.get(rand.nextInt(s.size())), adult, h.get(i), stations_per_lines, buses, timeLine);
			adult.setChild(child);
			adult.setJob(new Officer());
			context.add(adult);
			context.add(child);
			a.add(adult);
			c.add(child);
		}
		
		timeLine.setAdultsList(a);
		
		
		try {
			Stream<String> stream = Files.lines(Paths.get(path));
			stream.forEach(str -> {
				for (int i = 0; i < str.length(); i++) {
					switch (str.charAt(i)) {
						case 'H':
							this.house--;
							space.moveTo(h.get(this.house), i, this.aux);
							space.moveTo(a.get(this.house), i, this.aux);
							space.moveTo(c.get(this.house), i, this.aux);
							break;
						case 'O':
							this.office--;
							space.moveTo(o.get(this.office), i, this.aux);
							break;
						case 'S':
							this.school--;
							space.moveTo(s.get(this.school), i, this.aux);
							break;
						case 'B':
							this.bus--;
							space.moveTo(stations_per_lines.get(0).get(this.bus), i, this.aux);
							break;
						case 'P':
							this.park--;
							space.moveTo(p.get(this.park), i, this.aux);
							break;
						case ' ':
							Background b = new Background(space, grid);
							context.add(b);
							space.moveTo(b, i, this.aux);
							break;
					}
				}
				aux++;
			});
			stream.close();
		} catch (Exception e) {
			this.x = 50;
			this.y = 50;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return context;
	}
	
	public void addFamily(House house) {
		Random rand = new Random();
		Adult adult = new Adult(space, grid, context, o.get(rand.nextInt(o.size())), house, stations_per_lines, buses, timeLine, 0.5f, this);
		Child child = new Child(space, grid, s.get(rand.nextInt(s.size())), adult, house, stations_per_lines, buses, timeLine);
		adult.setChild(child);
		adult.setJob(new Officer());
		context.add(adult);
		context.add(child);
		NdPoint pt = space.getLocation(house);
		space.moveTo(adult, pt.getX(), pt.getY());
		space.moveTo(child, pt.getX(), pt.getY());
	}
	
	public int x = 0;
	public int y = 0;
	public int house = 0;
	public int office = 0;
	public int school = 0;
	public int park = 0;
	public int bus = 0;
	public int aux = 0;
	public List<School> s;
	public List<Office> o;
	public List<ArrayList<Place>> stations_per_lines;
	public List<Bus> buses;
	public TimeLine timeLine;
	public ContinuousSpace<Object> space;
	public Grid<Object> grid;
	Context<Object> context;
}