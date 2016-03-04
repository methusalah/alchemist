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

		if(Math.abs(neededRotation.angle.getValue()) < 0.01){
			b.clearAccumulatedTorque();
			b.setAngularVelocity(0);
			return;
		}
		
		double maxRotation = capacity.maxRotationSpeed;// * Pipeline.getSecondPerTick();
		double radPerTick = maxRotation * Pipeline.getSecondPerTick();
		if(Math.abs(b.getAngularVelocity()) >= Math.abs(neededRotation.angle.getValue() / Pipeline.getSecondPerTick())){
			// I will rotate too much, I have to decelerate
			b.setAngularVelocity(neededRotation.angle.getValue() / Pipeline.getSecondPerTick());
			//b.getTransform().setRotation();
			//b.setAngularDamping(500);
		} else {
			// I can accelerate
			b.setAngularDamping(0);
			b.applyTorque(maxRotation*Math.signum(neededRotation.angle.getValue()));
		}
	}
}
