package presentation.sceneView;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.EdgeFilteringMode;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import component.motion.PlanarStance;
import model.EditorPlatform;
import model.ECS.blueprint.Blueprint;
import model.state.DataState;
import model.state.DraggableCameraState;
import model.state.InstrumentUpdateState;
import model.state.SceneSelectorState;
import model.tempImport.RendererPlatform;
import presentation.base.AbstractPresenter;
import presentation.base.Viewer;
import util.geometry.geom2d.Point2D;

public class SceneViewPresenter extends AbstractPresenter<Viewer> {
	private static final int SHADOWMAP_SIZE = 4096;

	public SceneViewPresenter(Viewer viewer) {
		super(viewer);
		EditorPlatform.getScene().enqueue((app) -> createScene(app, EditorPlatform.getEntityData()));
	}
	
	static private boolean createScene(SimpleApplication app, EntityData ed) {
		RendererPlatform.setApp(app);
		app.getViewPort().addProcessor(new FilterPostProcessor(app.getAssetManager()));
		
		AppStateManager stateManager = app.getStateManager();
		
		stateManager.attach(new InstrumentUpdateState());

		DraggableCameraState cam = new DraggableCameraState(app.getCamera());
		cam.setRotationSpeed(0.001f);
		cam.setMoveSpeed(1f);
		stateManager.attach(cam);

		stateManager.attach(new SceneSelectorState());
		
		EntitySystem es = new EntitySystem(ed);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);

		// adding filters
		DirectionalLightShadowFilter sf = new DirectionalLightShadowFilter(RendererPlatform.getAssetManager(), SHADOWMAP_SIZE, 1);
		sf.setEnabled(false);
		sf.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
		sf.setShadowZExtend(SHADOWMAP_SIZE);
		RendererPlatform.getFilterPostProcessor().addFilter(sf);

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
		EditorPlatform.getScene().enqueue(app -> {
			EntityData ed = app.getStateManager().getState(DataState.class).getEntityData(); 
			EntityId newEntity = blueprint.createEntity(ed, null);
			PlanarStance stance = ed.getComponent(newEntity, PlanarStance.class); 
			if(stance != null){
				Point2D planarCoord = app.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan(screenCoord);
				ed.setComponent(newEntity, new PlanarStance(planarCoord, stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
			}
			return true;
		});
	}

}