package model.ES.processor.motion.physic;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.component.motion.physic.RandomDragging;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.PrecisionUtil;
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
