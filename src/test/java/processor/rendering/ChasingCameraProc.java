package processor.rendering;

import com.simsilica.es.Entity;

import javafx.geometry.Point3D;
import model.ECS.builtInComponent.Parenting;
import model.ECS.pipeline.Processor;

public class ChasingCameraProc extends Processor {
	
	@Override
	protected void registerSets() {
		registerDefault(PlanarStance.class, ChasingCamera.class, Parenting.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		ChasingCamera chasing = e.get(ChasingCamera.class);
		Parenting parenting = e.get(Parenting.class);
		PlanarStance targetStance = entityData.getComponent(parenting.getParent(), PlanarStance.class);
		
		if(targetStance == null)
			return;
		
		Point2D toTarget = targetStance.coord.getSubtraction(stance.coord);
		
		double minBrakingDistance = (chasing.getSpeed()*chasing.getSpeed())/(chasing.getDeceleration()*2);

		double newSpeed;
		if(minBrakingDistance >= toTarget.getLength())
			// deceleration
			newSpeed = Math.max(0, chasing.getSpeed()-chasing.getDeceleration()*LogicLoop.getSecondPerTick());
		else
			// acceleration
			newSpeed = Math.min(chasing.getMaxSpeed(), chasing.getSpeed()+chasing.getAcceleration()*LogicLoop.getSecondPerTick());
			
		toTarget = toTarget.getScaled(newSpeed*LogicLoop.getSecondPerTick());
		
		Point2D newCoord = stance.coord.getAddition(toTarget);
		Point3D pos = newCoord.get3D(stance.elevation);
		Point3D target = targetStance.coord.get3D(targetStance.elevation);
		
		setComp(e, new ChasingCamera(chasing.getMaxSpeed(), newSpeed, chasing.getAcceleration(), chasing.getDeceleration(), pos, target));
		setComp(e, new PlanarStance(newCoord, stance.orientation, stance.elevation, stance.upVector));
	}

}
