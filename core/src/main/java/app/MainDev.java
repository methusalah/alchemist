package app;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

import model.ModelManager;
import model.editor.ToolManager;
import model.editor.engines.CollisionTester;
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
import view.EditorView;
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
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

import controller.AppState;
import controller.SpatialSelector;
import controller.editor.EditorInputInterpreter;
import controller.editor.EditorState;
import controller.topdown.TopdownState;

public class MainDev extends CosmoVania {

	protected Injector injector;
	protected Collection<Module> modules;
	
	public static void main(String[] args) {
		CosmoVania.main(new MainDev());
	}

	@Override
	public void simpleInitApp() {
		populateInjector();
		
		MaterialManager.setAssetManager(assetManager);
		
		stateManager.attach(injector.getInstance(EditorState.class));

//		ModelManager.setNewBattlefield();
	}

	@Override
	public void simpleUpdate(float tpf) {
		float maxedTPF = Math.min(tpf, 0.1f);
		stateManager.update(maxedTPF);
	}

	@Subscribe
	public void handleEvent(AppStateChangeEvent e) {
		stateManager.attach(injector.getInstance(e.getControllerClass()));
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
				bind(ViewPort.class).annotatedWith(ViewPortRef.class).toInstance(viewPort);

				bind(Node.class).annotatedWith(GuiNodeRef.class).toInstance(guiNode);
				bind(ViewPort.class).annotatedWith(Names.named("GuiViewPort")).toInstance(guiViewPort);
				
				bind(AudioRenderer.class).annotatedWith(AudioRendererRef.class).toInstance(audioRenderer);
				bind(InputManager.class).annotatedWith(InputManagerRef.class).toInstance(inputManager);
				bind(Camera.class).toInstance(cam);

//				bind(TopdownState.class).annotatedWith(Names.named("TopdownState")).to(TopdownState.class).in(Singleton.class);
				bind(EditorState.class).in(Singleton.class);
				bind(SpatialSelector.class).annotatedWith(Names.named("EditorSpatialSelector")).;
				bind(EditorView.class).in(Singleton.class);
				bind(Float.class).annotatedWith(Names.named("CamElevation")).toInstance(10f);
			}
		});
		injector = Guice.createInjector(modules);
	}
}
