package view.drawingProcessors;

import util.math.AngleUtil;
import view.SpatialPool;
import view.math.TranslateUtil;

import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.simsilica.es.Entity;

import model.ES.component.Light;
import model.ES.component.motion.SpaceStance;
import controller.entityAppState.Processor;

public class LightProc extends Processor {

	@Override
	protected void registerSets() {
		register(SpaceStance.class, Light.class);
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
		Light l = e.get(Light.class);
		SpaceStance stance = e.get(SpaceStance.class);
		
		if(l.distance == Double.POSITIVE_INFINITY){
			manageDirectional(e, l, stance);
		} else if(l.innerAngle >= AngleUtil.FULL || l.outerAngle >= AngleUtil.FULL){
			managePoint(e, l, stance);
		} else {
			manageSpot(e, l, stance);
		}
	}

	private void manageDirectional(Entity e, Light l, SpaceStance stance){
		if(!SpatialPool.lights.containsKey(e))
			SpatialPool.lights.put(e.getId(), new DirectionalLight());
		DirectionalLight light = (DirectionalLight)SpatialPool.lights.get(e.getId());
		
		light.setColor(TranslateUtil.toColorRGBA(l.color).mult((float)l.intensity));
		light.setDirection(TranslateUtil.toVector3f(stance.getDirection()));
	}
	
	private void managePoint(Entity e, Light l, SpaceStance stance){
		if(!SpatialPool.lights.containsKey(e))
			SpatialPool.lights.put(e.getId(), new PointLight());
		PointLight light = (PointLight)SpatialPool.lights.get(e.getId());

		light.setColor(TranslateUtil.toColorRGBA(l.color).mult((float)l.intensity));
		light.setPosition(TranslateUtil.toVector3f(stance.getPosition()));
		light.setRadius((float)l.distance);
	}
	private void manageSpot(Entity e, Light l, SpaceStance stance){
		if(!SpatialPool.lights.containsKey(e))
			SpatialPool.lights.put(e.getId(), new SpotLight());
		SpotLight light = (SpotLight)SpatialPool.lights.get(e.getId());
		
		light.setColor(TranslateUtil.toColorRGBA(l.color).mult((float)l.intensity));
		light.setPosition(TranslateUtil.toVector3f(stance.getPosition()));
		light.setDirection(TranslateUtil.toVector3f(stance.getDirection()));
		light.setSpotInnerAngle((float)l.innerAngle);
		light.setSpotOuterAngle((float)l.outerAngle);
	}
}
