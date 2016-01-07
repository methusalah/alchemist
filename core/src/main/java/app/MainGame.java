package app;

import com.google.common.eventbus.Subscribe;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Spatial.CullHint;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

import controller.Controller;
import controller.ECS.EntitySystem;
import controller.topdown.TopdownCtrl;
import model.Command;
import model.ES.serial.BlueprintLibrary;
import model.world.WorldData;
import util.LogUtil;
import util.event.AppStateChangeEvent;
import view.material.MaterialManager;

public class MainGame extends CosmoVania {
	private Controller currentAppState;
	EntitySystem es;
	
	protected BitmapText fpsText;
	public static void main(String[] args) {
		new MainGame();
	}
	
	public MainGame() {
		LogUtil.init();
		AppFacade.setApp(this);
		start();
	}

	@Override
	public void simpleInitApp() {
		MaterialManager.initBaseMaterials();
		TopdownCtrl ctrl = new TopdownCtrl();
		stateManager.attach(ctrl);
		ctrl.setEnabled(true);
		
		EntityData ed = new DefaultEntityData();
		es = new EntitySystem(ed, new WorldData(ed), new Command());
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(true);
		es.initCommand(true);
		es.initLogic(true);
		
		BlueprintLibrary.getBlueprint("player ship").createEntity(ed, null);
		BlueprintLibrary.getBlueprint("enemy hammer").createEntity(ed, null);
	}

	
	@Override
	public void simpleUpdate(float tpf) {
		float maxedTPF = Math.min(tpf, 0.1f);
		stateManager.update(maxedTPF);
		debugger.reset();
		debugger.add(es.loop.getReport());
	}

	@Subscribe
	public void handleEvent(AppStateChangeEvent e) {
		currentAppState.setEnabled(false);
		currentAppState = stateManager.getState(e.getControllerClass());
		currentAppState.setEnabled(true);
	}
}