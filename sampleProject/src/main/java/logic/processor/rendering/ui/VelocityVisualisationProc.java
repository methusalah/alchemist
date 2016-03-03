package logic.processor.rendering.ui;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.brainless.alchemist.view.MaterialManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import com.simsilica.es.Entity;

import component.debug.VelocityViewing;
import component.motion.PlanarStance;
import logic.processor.Pool;
import util.geometry.geom2d.Point2D;

public class VelocityVisualisationProc extends BaseProcessor {

	@Override
	protected void registerSets() {
		registerDefault(VelocityViewing.class, PlanarStance.class);
	}

	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		
		for(VelocityView v : e.get(VelocityViewing.class).velocities.values()){
			if(!Pool.velocities.containsKey(v)){
				Geometry body = new Geometry("velocity");
				body.setMaterial(MaterialManager.getColor(TranslateUtil.toColorRGBA(v.color)));
				Pool.velocities.put(v, body);
				RendererPlatform.getMainSceneNode().attachChild(body);
			}
			Geometry g = (Geometry)Pool.velocities.get(v);
			Point2D end = stance.coord.getAddition(v.velocity);
			Line l = new Line(TranslateUtil.toVector3f(stance.coord.get3D(v.elevation)), TranslateUtil.toVector3f(end.get3D(v.elevation)));
			l.setLineWidth(v.thickness);
			g.setMesh(l);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
