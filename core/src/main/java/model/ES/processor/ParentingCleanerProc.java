package model.ES.processor;

import model.ES.component.Removed;
import model.ES.component.ToRemove;
import model.ES.component.hierarchy.Parenting;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class ParentingCleanerProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Parenting.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		if(entityData.getComponent(e.get(Parenting.class).getParent(), Removed.class) != null){
			// TODO find a way to test if an entity id have been "removed" from the entity data,
			// to avoid stacking useless Removed components 
			setComp(e, new ToRemove());
		}
	}

}
