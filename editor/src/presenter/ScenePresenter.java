package presenter;

import model.Command;
import model.state.DraggableCameraState;
import model.state.WorldLocaliserState;
import model.state.WorldToolState;
import model.world.WorldData;
import presenter.common.RunState;
import presenter.common.SceneInputManager;
import presenter.common.TopDownCamInputListener;
import view.controls.JmeImageView;
import view.jmeScene.GripInputListener;
import presenter.common.RunState.State;
import util.LogUtil;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.post.filters.TranslucentBucketFilter;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import controller.ECS.EntitySystem;
import controller.ECS.SceneSelectorState;
import app.AppFacade;

public class ScenePresenter {
	private final TopDownCamInputListener camera;
	
	public ScenePresenter() {
		if(EditorPlatform.getScene() == null){
			JmeImageView jmeScene = new JmeImageView();
			jmeScene.enqueue((app) -> createScene(app, EditorPlatform.getEntityData(), EditorPlatform.getWorldData(), EditorPlatform.getCommand()));
			EditorPlatform.setScene(jmeScene);
		}
		
		camera = new TopDownCamInputListener(EditorPlatform.getScene());
		EditorPlatform.getSceneInputManager().addListener(camera);
	}
	
	static private boolean createScene(SimpleApplication app, EntityData ed, WorldData world, Command command) {
		AppFacade.setApp(app);
		app.getViewPort().addProcessor(new FilterPostProcessor(app.getAssetManager()));
		
		AppStateManager stateManager = app.getStateManager();
		
		DraggableCameraState cam = new DraggableCameraState(app.getCamera());
		cam.setRotationSpeed(0.001f);
		cam.setMoveSpeed(1f);
		stateManager.attach(cam);

		stateManager.attach(new SceneSelectorState());
		stateManager.attach(new WorldToolState());
		stateManager.attach(new WorldLocaliserState());
		
		EntitySystem es = new EntitySystem(ed, world, command);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(false);
		es.initCommand(false);
		es.initLogic(false);

		AppFacade.getFilterPostProcessor().addFilter(new BloomFilter(BloomFilter.GlowMode.Objects));
		
		FXAAFilter fxaa = new FXAAFilter();
		fxaa.setReduceMul(0.9f);
		fxaa.setSpanMax(5f);
		fxaa.setSubPixelShift(0f);
		fxaa.setVxOffset(10f);
		AppFacade.getFilterPostProcessor().addFilter(fxaa);

		return true;
	}
	
	public JmeImageView getScene(){
		return EditorPlatform.getScene();
	}

	public SceneInputManager getInputManager() {
		return EditorPlatform.getSceneInputManager();
	}
	
}
