package projetSMA;

import repast.simphony.dataLoader.ContextBuilder;

public interface Job {

	public void doJob(Adult adult, ContextCreator cb);
}
