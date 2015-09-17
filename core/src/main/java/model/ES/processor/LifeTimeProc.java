package model.ES.processor;

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import model.ES.component.LifeTime;
import model.ES.component.ToRemove;
import controller.entityAppState.Processor;

public class LifeTimeProc extends Processor{

	@Override
	protected void registerSets() {
		register(LifeTime.class);
	}
	
	@Override
	protected void onUpdated(float elapsedTime) {
		for(EntitySet set : sets)
			for(Entity e : set){
				LifeTime life = e.get(LifeTime.class);
				if(life.getLifeStart()+life.getDuration() < System.currentTimeMillis())
					setComp(e, new ToRemove());
			}
				
	}

	
}
