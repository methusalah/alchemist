package logic.processor.logic.command;

import org.dyn4j.dynamics.Body;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.simsilica.es.Entity;

import component.motion.MotionCapacity;
import component.motion.PlanarNeededRotation;
import component.motion.physic.Physic;
import logic.processor.Pool;
import util.LogUtil;

public class NeededRotationProc extends BaseProcessor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarNeededRotation.class, MotionCapacity.class, Physic.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarNeededRotation neededRotation = e.get(PlanarNeededRotation.class);
		MotionCapacity capacity = e.get(MotionCapacity.class);
		
		Body b = Pool.bodies.get(e.getId());

		if(Math.abs(neededRotation.angle.getValue()) < 0.001){
			b.setAngularVelocity(0);
			return;
		}
		
		double maxRotation = capacity.maxRotationSpeed;// * Pipeline.getSecondPerTick();
		double radPerTick = maxRotation * Pipeline.getSecondPerTick();
		LogUtil.info("radian per tick : " + radPerTick + " / radian to go : " + neededRotation.angle.getValue());
		if(radPerTick * 5 >= neededRotation.angle.getValue()){
			b.setAngularVelocity(neededRotation.angle.getValue() / Pipeline.getSecondPerTick());
			//b.setAngularDamping(100);
		} else {
			b.setAngularDamping(0);
			b.setAngularVelocity(maxRotation*Math.signum(neededRotation.angle.getValue()));
		}
	}
}
