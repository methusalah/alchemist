package model.ES.processor.world;

import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.motion.PlanarStance;
import model.world.WorldData;
import util.LogUtil;
import util.geometry.geom2d.Point2D;

public class WorldProc extends Processor {
	
	private WorldData world;
	private Point2D oldCoord = null;
	
	public WorldProc(WorldData world) {
		this.world = world;
	}

	@Override
	protected void registerSets() {
		registerDefault(ChasingCamera.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		if(oldCoord == null || oldCoord.getDistance(stance.getCoord()) > 5){
			oldCoord = stance.getCoord();
			world.setCoord(stance.getCoord());
			LogUtil.info("hop");
		}
	}
	
	

}
