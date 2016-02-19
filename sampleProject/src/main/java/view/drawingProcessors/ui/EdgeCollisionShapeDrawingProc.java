package view.drawingProcessors.ui;

import java.util.HashMap;
import java.util.Map;

import model.ECS.Naming;
import model.ES.component.assets.Model;
import model.ES.component.motion.physic.EdgedCollisionShape;
import util.LogUtil;
import util.geometry.geom2d.Segment2D;
import util.geometry.geom3d.Segment3D;
import view.MaterialManager;
import view.math.TranslateUtil;
import app.AppFacade;
import main.java.model.ECS.pipeline.Processor;

import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import com.simsilica.es.Entity;

import SpatialPool;

public class EdgeCollisionShapeDrawingProc extends Processor {
	Map<String, Spatial> modelPrototypes = new HashMap<>();
	
	@Override
	protected void registerSets() {
		registerDefault(EdgedCollisionShape.class);
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		if(SpatialPool.models.keySet().contains(e.getId()))
			AppFacade.getMainSceneNode().detachChild(SpatialPool.models.remove(e.getId()));
	}

	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		if(SpatialPool.models.containsKey(e.getId()))
			AppFacade.getMainSceneNode().detachChild(SpatialPool.models.get(e.getId()));
		
		EdgedCollisionShape shape = e.get(EdgedCollisionShape.class);
		
		String name;
		Naming n = entityData.getComponent(e.getId(), Naming.class);
		if(n != null)
			name = n.getName() + " drawing";
		else
			name = "unnamed entity #"+e.getId() + " drawing";
		
		Node node = new Node(name);
		
		for(Segment2D s : shape.getEdges()){
			Geometry g = new Geometry(e.getId().getId() + " edge");
			g.setMesh(new Line(TranslateUtil.toVector3f(s.getStart(), 0.1), TranslateUtil.toVector3f(s.getEnd(), 0.1) ));
			g.setMaterial(MaterialManager.getLightingColor(ColorRGBA.LightGray));
			node.attachChild(g);
		}
		SpatialPool.models.put(e.getId(), node);
		AppFacade.getMainSceneNode().attachChild(node);
	}
}
