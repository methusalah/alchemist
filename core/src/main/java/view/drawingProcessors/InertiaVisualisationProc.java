package view.drawingProcessors;

import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarVelocityInertiaDebugger;
import model.ES.component.planarMotion.PlanarWipping;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.SpatialPool;
import view.material.MaterialManager;
import view.math.TranslateUtil;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;
import com.simsilica.es.Entity;

import controller.entityAppState.Processor;

public class InertiaVisualisationProc extends Processor {
	private AssetManager assetManager;
	

	@Override
	protected void onInitialized() {
		assetManager = app.getAssetManager();
	}
	
	@Override
	protected void registerSets() {
		register(PlanarVelocityInertiaDebugger.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		createModel(e);
	}
	
	@Override
	protected void onEntityRemoved(Entity e, float elapsedTime) {
		SpatialPool.inertias.remove(e.getId());
		SpatialPool.appliedVelocities.remove(e.getId());
		SpatialPool.resultingVelocity.remove(e.getId());
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		createModel(e);
	}
	
	private void createModel(Entity e){
		PlanarStance pos = e.get(PlanarStance.class);
		PlanarVelocityInertiaDebugger inertia = e.get(PlanarVelocityInertiaDebugger.class);
		Node root = app.getRootNode();
		
		if(root.hasChild(SpatialPool.inertias.get(e.getId())))
			app.getRootNode().detachChild(SpatialPool.inertias.get(e.getId()));
		
		if(root.hasChild(SpatialPool.appliedVelocities.get(e.getId())))
			app.getRootNode().detachChild(SpatialPool.appliedVelocities.get(e.getId()));

		if(root.hasChild(SpatialPool.resultingVelocity.get(e.getId())))
			app.getRootNode().detachChild(SpatialPool.resultingVelocity.get(e.getId()));

		SpatialPool.inertias.put(e.getId(), getArrow(pos.getCoord(), inertia.getVelocity(), ColorRGBA.Red));
		SpatialPool.appliedVelocities.put(e.getId(), getArrow(pos.getCoord(), inertia.getAppliedVelocity(), ColorRGBA.Blue));
		SpatialPool.resultingVelocity.put(e.getId(), getArrow(pos.getCoord(), inertia.getResultingVelocity(), ColorRGBA.Green));

		app.getRootNode().attachChild(SpatialPool.inertias.get(e.getId()));
		app.getRootNode().attachChild(SpatialPool.appliedVelocities.get(e.getId()));
		app.getRootNode().attachChild(SpatialPool.resultingVelocity.get(e.getId()));
	}
	
	private Node getArrow(Point2D start, Point2D direction, ColorRGBA color){
		Geometry body = new Geometry("body");
		Point2D end = start.getAddition(direction);
		
		
		Line l = new Line(TranslateUtil.toVector3f(start.get3D(0.2)), TranslateUtil.toVector3f(end.get3D(0.2)));
		l.setLineWidth(3);
		body.setMesh(l);
		body.setMaterial(MaterialManager.getColor(color));
		Node res = new Node();
		res.attachChild(body);
		return res;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
