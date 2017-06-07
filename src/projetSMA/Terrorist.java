package projetSMA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class Terrorist implements Job {
	public Terrorist(ContextCreator cb) {
		this.cb = cb;
	}
	
	public final void doJob(Adult adult, ContextCreator cb) {
		explode(adult);
		this.cb = cb;
	}
	
	private final void explode(final Adult adult) {
		Context<Object> context = ContextUtils.getContext(adult);
		
		final ContinuousSpace<Object> position = adult.pos;
		NdPoint myPoint  = position.getLocation(adult);

		final Iterator<Object> it = position.getObjects().iterator();
		final List<Adult> adults = new ArrayList();
		while (it.hasNext()) {
			final Object obj = it.next();
			if (obj instanceof Adult) {
				final Adult h = (Adult) obj;
				adults.add(h);
			}
		}
		
		NuclearExplosion nuke = new NuclearExplosion(adult.pos, adult.grid, context, 350);
		context.add(nuke);
		NdPoint pt = adult.pos.getLocation(adult);
		adult.pos.moveTo(nuke, pt.getX(), pt.getY());
		
		
		for (final Adult a : adults) {
			final NdPoint otherPoint = position.getLocation(a);
			if (otherPoint != null) {
				double dist = position.getDistance(myPoint, otherPoint);
				if (dist <= explosion_range) {
					final House house = a.getHouse();
					context.remove(a.getChild());
					new Graves(adult.pos, adult.grid, context, 0, 0, adult.pos.getLocation(house), 28 * 500, cb);
					context.remove(house);
					context.remove(a);
					context.remove(this);
				}
			}
		}
	}
	
	protected double explosion_range = 2.0;
	protected ContextCreator cb;
}