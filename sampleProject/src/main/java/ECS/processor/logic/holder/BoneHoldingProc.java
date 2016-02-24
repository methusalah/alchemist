package ECS.processor.logic.holder;

import com.simsilica.es.Entity;

import ECS.component.assets.Skeleton;
import ECS.component.motion.BoneHolding;
import ECS.component.motion.SpaceStance;
import model.ECS.pipeline.Processor;

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
