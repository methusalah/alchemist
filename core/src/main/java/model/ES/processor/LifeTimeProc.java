package model.ES.processor;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import model.ES.component.LifeTime;
import model.ES.component.ToRemove;
import controller.ECS.Processor;

public class LifeTimeProc extends Processor{

	@Override
	protected void registerSets() {
		register(LifeTime.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		LifeTime life = e.get(LifeTime.class);
		if(life.lifeStart+life.duration < System.currentTimeMillis())
			setComp(e, new ToRemove());
	}
}
