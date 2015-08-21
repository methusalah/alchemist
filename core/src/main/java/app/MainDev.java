package app;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

import model.ModelManager;
import model.editor.ToolManager;
import model.editor.engines.CollisionTester;
import util.LogUtil;
import util.annotation.AppSettingsRef;
import util.annotation.AssetManagerRef;
import util.annotation.AudioRendererRef;
import util.annotation.CameraRef;
import util.annotation.GuiNodeRef;
import util.annotation.InputManagerRef;
import util.annotation.RootNodeRef;
import util.annotation.StateManagerRef;
import util.annotation.ViewPortRef;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import view.EditorView;
import view.TopdownView;
import view.View;
import view.mapDrawing.MapDrawer;
import view.material.MaterialManager;

import com.google.common.eventbus.Subscribe;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

import controller.AppState;
import controller.SpatialSelector;
import controller.editor.EditorInputInterpreter;
import controller.editor.EditorState;
import controller.topdown.TopdownState;
import de.lessvoid.nifty.Nifty;

public class MainDev extends CosmoVania {

	protected Injector injector;
	protected Collection<Module> modules;
	NiftyJmeDisplay niftyDisplay;
	private AppState currentAppState;
	
	public static void main(String[] args) {
		CosmoVania.main(new MainDev());
	}

	@Override
	public void simpleInitApp() {
		niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		guiViewPort.addProcessor(niftyDisplay);

		populateInjector();
		MaterialManager.setAssetManager(assetManager);
		
		currentAppState = injector.getInstance(EditorState.class);
		stateManager.attach(currentAppState);

		EventManager.register(this);
		
		ModelManager.setNewBattlefield();
	}

	@Override
	public void simpleUpdate(float tpf) {
		float maxedTPF = Math.min(tpf, 0.1f);
		stateManager.update(maxedTPF);
	}

	@Subscribe
	public void handleEvent(AppStateChangeEvent e) {
		stateManager.detach(currentAppState);
		currentAppState = injector.getInstance(e.getControllerClass());
		stateManager.attach(currentAppState);
		
	}
	
	private void populateInjector(){
		this.modules = new LinkedList<Module>();
		// register new instances to Guice (DI)
		this.modules.add(new AbstractModule() {

			@Override
			protected void configure() {

				bind(AssetManager.class).toInstance(assetManager);
				bind(AppSettings.class).annotatedWith(AppSettingsRef.class).toInstance(settings);
				bind(AppStateManager.class).annotatedWith(StateManagerRef.class).toInstance(stateManager);
				bind(Node.class).annotatedWith(RootNodeRef.class).toInstance(rootNode);
				bind(ViewPort.class).toInstance(viewPort);

				bind(Node.class).annotatedWith(GuiNodeRef.class).toInstance(guiNode);
				bind(ViewPort.class).annotatedWith(Names.named("GuiViewPort")).toInstance(guiViewPort);
				
				bind(AudioRenderer.class).annotatedWith(AudioRendererRef.class).toInstance(audioRenderer);
				bind(InputManager.class).toInstance(inputManager);
				bind(Camera.class).toInstance(cam);
				
				bind(Nifty.class).toInstance(niftyDisplay.getNifty());

//				bind(TopdownState.class).annotatedWith(Names.named("TopdownState")).to(TopdownState.class).in(Singleton.class);
				bind(EditorState.class).in(Singleton.class);
				bind(EditorView.class).in(Singleton.class);
				bind(TopdownState.class).in(Singleton.class);
				bind(TopdownView.class).in(Singleton.class);
			}
		});
		injector = Guice.createInjector(modules);
	}
}
