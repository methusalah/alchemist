package model.ES.processor.holder;

import model.ES.component.motion.SpaceStance;
import model.ES.component.relation.BoneHolding;
import model.ES.component.visuals.Skeleton;

import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class BoneHoldingProc extends Processor {

	@Override
	protected void registerSets() {
		register(BoneHolding.class, SpaceStance.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		BoneHolding holded = e.get(BoneHolding.class);
		Skeleton sk = entityData.getComponent(holded.holder, Skeleton.class);
		setComp(e, new SpaceStance(sk.bonePositions.get(holded.positionBoneName), sk.boneDirections.get(holded.directionBoneName)));
	}
}
