package app;

import java.util.Collection;
import java.util.LinkedList;

import model.ModelManager;
import model.ES.component.ModelComp;
import model.ES.component.motion.PlanarInertia;
import model.ES.component.motion.PlanarMotionCapacity;
import model.ES.component.motion.PlanarPosition;
import model.ES.component.motion.PlayerControl;
import model.ES.processor.PlayerControlProc;
import model.ES.processor.motion.InertiaMotionProc;
import model.ES.processor.motion.PlanarRotationProc;
import model.ES.processor.motion.PlanarThrustProc;
import util.annotation.AppSettingsRef;
import util.annotation.AudioRendererRef;
import util.annotation.GuiNodeRef;
import util.annotation.RootNodeRef;
import util.annotation.StateManagerRef;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import view.EditorView;
import view.TopdownView;
import view.drawingProcessors.InertiaVisualisationProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.PlacingModelProc;
import view.material.MaterialManager;

import com.google.common.eventbus.Subscribe;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.simsilica.es.EntityData;

import controller.AppState;
import controller.editor.EditorState;
import controller.entityAppState.EntityDataAppState;
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
		
		stateManager.attach(new EntityDataAppState());
		stateManager.attach(new PlayerControlProc());
		stateManager.attach(new PlanarRotationProc());
		stateManager.attach(new PlanarThrustProc());
		stateManager.attach(new InertiaMotionProc());
		stateManager.attach(new ModelProc(assetManager));
		stateManager.attach(new PlacingModelProc());
		stateManager.attach(new InertiaVisualisationProc());
		
		EntityData ed = stateManager.getState(EntityDataAppState.class).getEntityData();
		ModelManager.entityData = ed;
		ModelManager.shipID = ed.createEntity();
		ed.setComponent(ModelManager.shipID, new PlayerControl());
		ed.setComponent(ModelManager.shipID, new PlanarPosition(new Point2D(1, 1), 0.5));
		ed.setComponent(ModelManager.shipID, new PlanarInertia(Point2D.ORIGIN));
		ed.setComponent(ModelManager.shipID, new PlanarMotionCapacity(2, AngleUtil.toRadians(500), 500, 5));
		ed.setComponent(ModelManager.shipID, new ModelComp("human/htank/htank03c.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
		

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
