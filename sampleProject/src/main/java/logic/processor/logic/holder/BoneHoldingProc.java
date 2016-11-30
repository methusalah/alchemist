package logic.processor.logic.holder;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.assets.Skeleton;
import component.motion.BoneHolding;
import component.motion.SpaceStance;

public class BoneHoldingProc extends BaseProcessor {

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
