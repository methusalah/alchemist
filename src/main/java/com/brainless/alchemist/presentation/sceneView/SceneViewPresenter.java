package com.brainless.alchemist.presentation.sceneView;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.blueprint.Blueprint;
import com.brainless.alchemist.model.state.DataState;
import com.brainless.alchemist.model.state.DraggableCameraState;
import com.brainless.alchemist.model.state.SceneSelectorState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.presentation.base.AbstractPresenter;
import com.brainless.alchemist.presentation.base.Viewer;
import com.brainless.alchemist.view.ViewPlatform;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.EdgeFilteringMode;
import com.simsilica.es.EntityData;

import util.geometry.geom2d.Point2D;

public class SceneViewPresenter extends AbstractPresenter<Viewer> {
	private static final int SHADOWMAP_SIZE = 4096;

	public SceneViewPresenter(Viewer viewer) {
		super(viewer);
		ViewPlatform.getScene().enqueue(app -> createScene(app, EditorPlatform.getEntityData()));
	}
	
	static private boolean createScene(SimpleApplication app, EntityData ed) {
		//RendererPlatform.setApp(app);
		app.getViewPort().addProcessor(new FilterPostProcessor(app.getAssetManager()));
		
		AppStateManager stateManager = app.getStateManager();
		
		DraggableCameraState cam = new DraggableCameraState(app.getCamera());
		cam.setRotationSpeed(0.001f);
		//cam.setMoveSpeed(0.001f);
		stateManager.attach(cam);

		stateManager.attach(new DataState(EditorPlatform.getEntityData()));
		stateManager.attach(new SceneSelectorState());
		
		// adding filters
		DirectionalLightShadowFilter sf = new DirectionalLightShadowFilter(RendererPlatform.getAssetManager(), SHADOWMAP_SIZE, 1);
		sf.setEnabled(false);
		sf.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
		sf.setShadowZExtend(SHADOWMAP_SIZE);
		//RendererPlatform.getFilterPostProcessor().addFilter(sf);

//		DirectionalLightShadowRenderer sr = new DirectionalLightShadowRenderer(AppFacade.getAssetManager(), SHADOWMAP_SIZE, 1);
//		//sr.setEnabled(false);
//		sr.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
//		sr.setShadowZExtend(SHADOWMAP_SIZE);
//		//AppFacade.getFilterPostProcessor().addFilter(sr);

		
		BloomFilter bf = new BloomFilter(BloomFilter.GlowMode.Objects);
		//bf.setEnabled(false);
		RendererPlatform.getFilterPostProcessor().addFilter(bf);
		
		FXAAFilter fxaa = new FXAAFilter();
		//fxaa.setEnabled(false);
		fxaa.setReduceMul(0.9f);
		fxaa.setSpanMax(5f);
		fxaa.setSubPixelShift(0f);
		fxaa.setVxOffset(10f);
		RendererPlatform.getFilterPostProcessor().addFilter(fxaa);

		return true;
	}
	
	public void createEntityAt(Blueprint blueprint, Point2D screenCoord){
		SceneViewBehavior.createEntityFunction.apply(blueprint, screenCoord);
		
	}

}
