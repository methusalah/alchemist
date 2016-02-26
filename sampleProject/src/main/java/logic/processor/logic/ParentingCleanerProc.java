package logic.processor.logic;

import com.brainless.alchemist.model.ECS.builtInComponent.Parenting;
import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.lifeCycle.Removed;
import component.lifeCycle.ToRemove;

public class ParentingCleanerProc extends BaseProcessor {

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
