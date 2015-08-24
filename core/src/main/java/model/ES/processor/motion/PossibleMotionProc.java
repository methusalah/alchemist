package model.ES.processor.motion;

import model.ES.component.motion.PlanarDesiredMotion;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlanarPossibleMotion;
import model.ES.events.PossibleMotionAttachedEvent;
import model.ES.events.TheoricalMotionAttachedEvent;
import util.entity.CompMask;
import util.entity.Entity;
import util.entity.EntityGroup;
import util.entity.EntityPool;
import util.entity.Processor;
import util.event.EventManager;

public class PossibleMotionProc extends Processor {
	
	public PossibleMotionProc() {
		mask = new CompMask(PlanarDesiredMotion.class, PlanarMotionCapacity.class, PlanarInertia.class);
	}

	@Override
	protected void onUpdate(float elapsedTime) {
		for(Entity e : entities.getCopyList()){
			AdaptMotionToCapacity(e);
		}
	}

	private void AdaptMotionToCapacity(Entity e) {
		PlanarDesiredMotion motion = e.get(PlanarDesiredMotion.class);
		PlanarInertia inertia = e.get(PlanarInertia.class);
		PlanarMotionCapacity capacity = e.get(PlanarMotionCapacity.class);
		MotionAdapter adapter = new MotionAdapter(motion, capacity, inertia);

		adapter.adaptSpeed();
		adapter.adaptRotation();
		
		e.remove(PlanarDesiredMotion.class);
		e.add(adapter.getResultingMotion());
		
	}
}
