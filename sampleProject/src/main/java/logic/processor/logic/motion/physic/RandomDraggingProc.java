package logic.processor.logic.motion.physic;

import com.brainless.alchemist.model.ECS.pipeline.Processor;
import com.simsilica.es.Entity;

import component.motion.physic.Dragging;
import component.motion.physic.RandomDragging;
import util.math.RandomUtil;

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
