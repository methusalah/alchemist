package model.ES.processor.holder;

import java.util.List;

import util.geometry.geom3d.Point3D;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import model.ES.component.holder.HolderOnBone;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarThrust;
import model.ES.component.spaceMotion.SpaceStance;
import model.ES.component.visuals.Skeleton;
import controller.entityAppState.Processor;

public class HolderOnBoneProc extends Processor {

	@Override
	protected void registerSets() {
		register(HolderOnBone.class, Skeleton.class);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		HolderOnBone holder = e.get(HolderOnBone.class);
		Skeleton sk = e.get(Skeleton.class);
		
		if(!sk.isInitialized())
			return;
		
		List<EntityId> ids = holder.getHolded();
		List<String> positionBones = holder.getPositionBones();
		List<String> directionBones = holder.getDirectionBones();
		
		for(int i = 0; i < ids.size(); i++){
			Entity child = entityData.getEntity(ids.get(i));
			Point3D newPosition = sk.getBonePositions().get(positionBones.get(i));
			Point3D newDirection = sk.getBoneDirections().get(directionBones.get(i));
			
			PlanarStance childPlanarStance = child.get(PlanarStance.class);
			if(childPlanarStance != null){
				setComp(child, new PlanarStance(newPosition.get2D(), newDirection.get2D().getAngle(), newPosition.z, childPlanarStance.getUpVector()));
			}
				
			SpaceStance childSpaceStance = child.get(SpaceStance.class);
			if(childSpaceStance != null){
				setComp(child, new SpaceStance(newPosition, newDirection));
			}
				
			
		}
			
	}
}
