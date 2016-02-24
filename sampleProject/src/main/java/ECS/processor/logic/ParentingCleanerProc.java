package ECS.processor.logic;

import com.simsilica.es.Entity;

import ECS.component.lifeCycle.Removed;
import ECS.component.lifeCycle.ToRemove;
import model.ECS.builtInComponent.Parenting;
import model.ECS.pipeline.Processor;

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
