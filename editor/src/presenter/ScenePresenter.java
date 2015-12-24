package presenter;

import model.Command;
import model.state.DraggableCameraState;
import model.state.WorldLocaliserState;
import model.state.WorldToolState;
import model.world.WorldData;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;

import controller.ECS.EntitySystem;
import controller.ECS.SceneSelectorState;
import app.AppFacade;
import application.EditorPlatform;


public class ScenePresenter {
	
	public ScenePresenter() {
		if(EditorPlatform.getScene() == null){
			JmeForImageView scene = new JmeForImageView();
			scene.enqueue((app) -> createScene(app, EditorPlatform.getEntityData(), EditorPlatform.getWorldData(), EditorPlatform.getCommand()));
			EditorPlatform.setScene(scene);
		}
	}
	
	static private boolean createScene(SimpleApplication app, EntityData ed, WorldData world, Command command) {
		AppFacade.setApp(app);
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
		return true;
	}
	
}
