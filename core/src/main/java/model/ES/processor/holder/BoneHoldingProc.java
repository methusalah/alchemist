package model.ES.processor.holder;

import model.ES.component.assets.Skeleton;
import model.ES.component.motion.BoneHolding;
import model.ES.component.motion.SpaceStance;

import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class BoneHoldingProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(BoneHolding.class, SpaceStance.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		BoneHolding holded = e.get(BoneHolding.class);
		Skeleton sk = entityData.getComponent(holded.holder, Skeleton.class);
		setComp(e, new SpaceStance(sk.bonePositions.get(holded.positionBoneName), sk.boneDirections.get(holded.directionBoneName)));
	}
}
