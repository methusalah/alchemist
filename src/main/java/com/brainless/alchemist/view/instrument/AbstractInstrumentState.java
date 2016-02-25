package com.brainless.alchemist.view.instrument;

import java.util.ArrayList;
import java.util.List;

import com.brainless.alchemist.model.state.SceneSelectorState;
import com.brainless.alchemist.model.state.SpatialSelector;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.brainless.alchemist.view.instrument.customControl.InstrumentPart;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import util.geometry.geom3d.Point3D;

public abstract class AbstractInstrumentState extends AbstractAppState {
	protected final String Name = this.getClass().getSimpleName();
	
	protected final InstrumentPresenter presenter;
	protected final List<InstrumentPart> parts = new ArrayList<>();
	protected SceneSelectorState selector;
	
	protected Node drawnNode = new Node(Name + " view");
	protected Node gripNode = new Node(Name + " grip");
	protected Geometry pointedGeometry;
	
	public AbstractInstrumentState(InstrumentPresenter presenter) {
		this.presenter = presenter;
		drawnNode.setQueueBucket(Bucket.Transparent);
		createInstrumentParts();
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		selector = stateManager.getState(SceneSelectorState.class);
	}

	@Override
	public void update(float tpf) {
		Geometry lastPointed = pointedGeometry;
		pointedGeometry = SpatialSelector.getPointedGeometry(gripNode, selector.getCoordInScreenSpace());
		if(lastPointed != pointedGeometry){
			for(InstrumentPart p : parts)
				p.setHover(p.containsGrip(pointedGeometry));
		}

		Point3D pos = presenter.getPosition();
		if(pos != null){
			// translation
			drawnNode.setLocalTranslation(TranslateUtil.toVector3f(pos));
			gripNode.setLocalTranslation(TranslateUtil.toVector3f(pos));
			
			// rotation
			double orientation = presenter.getOrientation();
			drawnNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)orientation));
			gripNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)orientation));
			
			// scale
			double camDistance = RendererPlatform.getCamera().getLocation().distance(TranslateUtil.toVector3f(pos));
			drawnNode.setLocalScale((float)camDistance);
			gripNode.setLocalScale((float)camDistance);
		}
	}
	
	public void startDrag(){
		for(InstrumentPart p : parts)
			if(p.containsGrip(pointedGeometry))
				p.select();
		presenter.startDrag(selector.getCoordInScreenSpace());
	}
	public void stopDrag(){
		presenter.stopDrag();
	}
	
	public void drag(){
		presenter.drag(selector.getCoordInScreenSpace());
	}
	
	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		RendererPlatform.getInstrumentNode().attachChild(drawnNode);

//		stateManager.getState(InstrumentUpdateState.class).addNode(drawnNode);
//		Camera c = AppFacade.getCamera().clone();
//		c.setViewPort(0, 1f, 0, 1f);
//		ViewPort viewPort2 = AppFacade.getApp().getRenderManager().createMainView("test", c);
//		viewPort2.attachScene(drawnNode);
//		viewPort2.setClearFlags(true, true, false);
//		drawnNode.addLight(new AmbientLight());
//		FilterPostProcessor fpp = new FilterPostProcessor(AppFacade.getAssetManager());
//		fpp.addFilter(new FXAAFilter());
//		fpp.addFilter(new BloomFilter());
//		
//		viewPort2.addProcessor(fpp);
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		drawnNode.removeFromParent();
		for(InstrumentPart p : parts)
			p.setHover(false);
	}
	
	abstract protected void createInstrumentParts();
}
