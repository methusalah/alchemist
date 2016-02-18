package model.ES.processor;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import main.java.model.ECS.pipeline.Processor;
import model.ES.component.lifeCycle.LifeTime;
import model.ES.component.lifeCycle.ToRemove;
import model.tempImport.LogicLoop;

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
		setComp(e, new LifeTime(life.getDuration()-LogicLoop.getMillisPerTick()));
	}
}
