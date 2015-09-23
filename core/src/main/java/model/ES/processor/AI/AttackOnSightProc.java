package model.ES.processor.AI;

import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.component.senses.Sighting;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;

public class AttackOnSightProc extends Processor {

	@Override
	protected void registerSets() {
		register(Sighting.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		Sighting s = e.get(Sighting.class);
		
		for(EntityId eid : s.entitiesInSight){
			PlayerControl control = entityData.getComponent(eid, PlayerControl.class);
			if(control != null){
				attack(e, eid);
			}
		}
	}
	
	private void attack(Entity attacker, EntityId target){
		
		PlanarStance stance = attacker.get(PlanarStance.class);
		PlanarStance ennemyStance = entityData.getComponent(target, PlanarStance.class);
		
		Point2D vector = ennemyStance.getCoord().getSubtraction(stance.getCoord());
		// aim target
		double neededAngle = vector.getAngle();
		double neededRotAngle;
		int turn = AngleUtil.getTurn(stance.getOrientation(), neededAngle);
		if(turn != AngleUtil.NONE){
			double diff = AngleUtil.getSmallestDifference(stance.getOrientation(), neededAngle);
			if(turn >= 0)
				neededRotAngle = diff;
			else
				neededRotAngle = -diff;
			setComp(attacker, new PlanarNeededRotation(neededRotAngle));
		}
		
		if(vector.getLength() > 5){
			// approach target
			setComp(attacker, new PlanarNeededThrust(vector.getNormalized()));
		} else if(vector.getLength() < 2){
			// flee fast target
			setComp(attacker, new PlanarNeededThrust(vector.getNegation().getNormalized()));
		} else {
			setComp(attacker, new PlanarNeededThrust(vector.getNormalized().getRotation(AngleUtil.RIGHT)));
		}
		
	}
}
