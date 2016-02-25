package logic.processor.logic;

import com.simsilica.es.Entity;

import component.lifeCycle.LifeTime;
import component.lifeCycle.ToRemove;
import model.ECS.pipeline.Pipeline;
import model.ECS.pipeline.Processor;

public class LifeTimeProc extends Processor{

	@Override
	protected void registerSets() {
		registerDefault(LifeTime.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		LifeTime life = e.get(LifeTime.class);
		if(life.duration <= 0)
			setComp(e, new ToRemove());
		setComp(e, new LifeTime(life.getDuration() - Pipeline.getMillisPerTick()));
	}
}
