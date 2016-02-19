package processor.logic.motion.physic;

import com.simsilica.es.Entity;

import model.ECS.pipeline.Processor;

public class RandomDraggingProc extends Processor {
	@Override
	protected void registerSets() {
		registerDefault(RandomDragging.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		RandomDragging dr = e.get(RandomDragging.class);
		double dragcoef = dr.getDragCoef() + RandomUtil.between(0, dr.getDragCoefRange());
		setComp(e, new Dragging(dragcoef));
		removeComp(e, RandomDragging.class);
	}
}
