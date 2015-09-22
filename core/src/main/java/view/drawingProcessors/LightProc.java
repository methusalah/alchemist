package view.drawingProcessors;

import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.SpatialPool;
import view.math.TranslateUtil;

import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.simsilica.es.Entity;

import app.AppFacade;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.SpaceStance;
import model.ES.component.visuals.Lighting;
import controller.entityAppState.Processor;

public class LightProc extends Processor {

	@Override
	protected void registerSets() {
		register(SpaceStance.class, Lighting.class);
		register(PlanarStance.class, Lighting.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		manage(e, elapsedTime);
	}
	
	private void manage(Entity e, float elapsedTime){
		Lighting l = e.get(Lighting.class);
		SpaceStance spaceStance = e.get(SpaceStance.class);
		PlanarStance planarStance = e.get(PlanarStance.class);
		Point3D position, direction;
		if(spaceStance != null){
			position = spaceStance.getPosition();
			direction = spaceStance.getDirection();
		} else {
			position = planarStance.getCoord().get3D(planarStance.getElevation());
			direction = Point3D.UNIT_X.getRotationAroundZ(planarStance.getOrientation());
		}
		
		if(l.distance == Double.POSITIVE_INFINITY){
			manageDirectional(e, l, position, direction);
		} else if(l.innerAngle >= AngleUtil.FULL || l.outerAngle >= AngleUtil.FULL){
			managePoint(e, l, position, direction);
		} else {
			manageSpot(e, l, position, direction);
		}
	}

	private void manageDirectional(Entity e, Lighting l, Point3D position, Point3D direction){
		if(!SpatialPool.lights.containsKey(e.getId())){
			DirectionalLight light = new DirectionalLight();
			SpatialPool.lights.put(e.getId(), light);
			AppFacade.getRootNode().addLight(light);
		}
		DirectionalLight light = (DirectionalLight)SpatialPool.lights.get(e.getId());
		
		light.setColor(TranslateUtil.toColorRGBA(l.color).mult((float)(l.intensity*l.activationRate)));
		light.setDirection(TranslateUtil.toVector3f(direction));
	}
	
	private void managePoint(Entity e, Lighting l, Point3D position, Point3D direction){
		if(!SpatialPool.lights.containsKey(e.getId())){
			PointLight light = new PointLight();
			SpatialPool.lights.put(e.getId(), light);
			AppFacade.getRootNode().addLight(light);
		}
		PointLight light = (PointLight)SpatialPool.lights.get(e.getId());

		light.setColor(TranslateUtil.toColorRGBA(l.color).mult((float)(l.intensity*l.activationRate)));
		light.setPosition(TranslateUtil.toVector3f(position));
		light.setRadius((float)l.distance);
	}
	private void manageSpot(Entity e, Lighting l, Point3D position, Point3D direction){
		if(!SpatialPool.lights.containsKey(e.getId())){
			SpotLight light = new SpotLight();
			SpatialPool.lights.put(e.getId(), light);
			AppFacade.getRootNode().addLight(light);
		}
		SpotLight light = (SpotLight)SpatialPool.lights.get(e.getId());
		
		light.setColor(TranslateUtil.toColorRGBA(l.color).mult((float)(l.intensity*l.activationRate)));
		light.setSpotRange((float)l.distance);
		light.setPosition(TranslateUtil.toVector3f(position));
		light.setDirection(TranslateUtil.toVector3f(direction));
		light.setSpotInnerAngle((float)l.innerAngle);
		light.setSpotOuterAngle((float)l.outerAngle);
	}
}
