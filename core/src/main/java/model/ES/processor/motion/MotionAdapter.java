package model.ES.processor.motion;

import model.ES.component.motion.PlanarDesiredMotion;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPossibleMotion;

public class MotionAdapter {
	
	private final PlanarDesiredMotion motion;
	private final PlanarMotionCapacity capacity;
	private final PlanarInertia inertia;
	private double possibleRotation = 0;
	private double possibleDistance = 0;
	
	public MotionAdapter(PlanarDesiredMotion motion, PlanarMotionCapacity capacity, PlanarInertia inertia) {
		this.motion = motion;
		this.capacity = capacity;
		this.inertia = inertia;
	}
	
	public void adaptSpeed(){
		if(motion.getDistance() == 0)
			decSpeed();
		else
			incSpeed();
	}
	
	public void adaptRotation(){
		if(motion.getRotation() != 0){
			double maxRotation = capacity.getMaxRotationSpeed() * motion.getElapsedTime();
			maxRotation = Math.min(Math.abs(motion.getRotation()), possibleRotation);
			possibleRotation = maxRotation*Math.signum(motion.getRotation());
		}
	}
	
	public void incSpeed() {
		if(inertia.getSpeed() < capacity.getMaxSpeed()){
			double newSpeed = inertia.getSpeed() + capacity.getAcceleration()*motion.getElapsedTime();
			newSpeed = Math.min(capacity.getMaxSpeed(), newSpeed);
			possibleDistance = newSpeed;
		}
	}

	public void decSpeed() {
		if(inertia.getSpeed() > 0){
			double newSpeed = inertia.getSpeed() - capacity.getDeceleration()*motion.getElapsedTime();
			newSpeed = Math.max(0, newSpeed);
			possibleDistance = newSpeed;
		}
	}

	public PlanarPossibleMotion getResultingMotion() {
		return new PlanarPossibleMotion(possibleDistance, possibleRotation, motion.getElapsedTime());
	}

}
