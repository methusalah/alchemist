package view.drawingProcessors;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import app.AppFacade;
import controller.entityAppState.Processor;
import model.ES.component.debug.VelocityDebugger;
import model.ES.component.debug.VelocityDebug;
import model.ES.component.planarMotion.PlanarStance;
import util.geometry.geom2d.Point2D;
import view.SpatialPool;
import view.material.MaterialManager;
import view.math.TranslateUtil;

public class VelocityVisualisationProc extends Processor {
	private AssetManager assetManager;
	

	@Override
	protected void onInitialized() {
		assetManager = app.getAssetManager();
	}
	
	@Override
	protected void registerSets() {
		register(VelocityDebugger.class, PlanarStance.class);
	}

	@Override
	protected void onUpdated(float elapsedTime) {
		for(EntitySet set : sets)
			for(Entity e : set){
				manage(e);
			}
	}
	
	
	private void manage(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		
		for(VelocityDebug v : e.get(VelocityDebugger.class).velocities){
			if(!SpatialPool.velocities.containsKey(v)){
				Geometry body = new Geometry("body");
				body.setMaterial(MaterialManager.getColor(TranslateUtil.toColorRGBA(v.color)));
				SpatialPool.velocities.put(v, body);
				AppFacade.getRootNode().attachChild(body);
			}
			Geometry g = (Geometry)SpatialPool.velocities.get(v);
			Point2D end = stance.getCoord().getAddition(v.velocity);
			Line l = new Line(TranslateUtil.toVector3f(stance.getCoord().get3D(0.2)), TranslateUtil.toVector3f(end.get3D(0.2)));
			l.setLineWidth(3);
			g.setMesh(l);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
