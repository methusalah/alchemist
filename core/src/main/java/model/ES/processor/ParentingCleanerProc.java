package model.ES.processor;

import model.ES.component.ToRemove;
import model.ES.component.relation.Parenting;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class ParentingCleanerProc extends Processor {

	@Override
	protected void registerSets() {
		register(Parenting.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		if(entityData.getEntity(e.get(Parenting.class).getParent()) == null)
			setComp(e, new ToRemove());
	}

}
