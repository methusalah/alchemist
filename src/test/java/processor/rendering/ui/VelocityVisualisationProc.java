package processor.rendering.ui;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import com.simsilica.es.Entity;

import commonLogic.SpatialPool;
import commonLogic.VelocityView;
import component.debug.VelocityViewing;
import component.motion.PlanarStance;
import model.ECS.pipeline.Processor;
import model.tempImport.RendererPlatform;
import model.tempImport.TranslateUtil;
import util.geometry.geom2d.Point2D;
import view.MaterialManager;

public class VelocityVisualisationProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(VelocityViewing.class, PlanarStance.class);
	}

	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		
		for(VelocityView v : e.get(VelocityViewing.class).velocities.values()){
			if(!SpatialPool.velocities.containsKey(v)){
				Geometry body = new Geometry("velocity");
				body.setMaterial(MaterialManager.getColor(TranslateUtil.toColorRGBA(v.color)));
				SpatialPool.velocities.put(v, body);
				RendererPlatform.getMainSceneNode().attachChild(body);
			}
			Geometry g = (Geometry)SpatialPool.velocities.get(v);
			Point2D end = stance.coord.getAddition(v.velocity);
			Line l = new Line(TranslateUtil.toVector3f(stance.coord.get3D(v.elevation)), TranslateUtil.toVector3f(end.get3D(v.elevation)));
			l.setLineWidth(v.thickness);
			g.setMesh(l);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
