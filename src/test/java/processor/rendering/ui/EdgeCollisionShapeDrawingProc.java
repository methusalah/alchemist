package processor.rendering.ui;

import java.util.HashMap;
import java.util.Map;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import com.simsilica.es.Entity;

import commonLogic.SpatialPool;
import component.motion.physic.EdgedCollisionShape;
import model.ECS.builtInComponent.Naming;
import model.ECS.pipeline.Processor;
import model.tempImport.AppFacade;
import model.tempImport.TranslateUtil;
import util.geometry.geom2d.Segment2D;
import view.MaterialManager;

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
