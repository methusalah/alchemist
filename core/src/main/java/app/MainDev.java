package app;

import java.awt.Color;

import model.ModelManager;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.planarMotion.PlanarMotionCapacity;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarWippingInertia;
import model.ES.component.planarMotion.PlayerControl;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.spaceMotion.SpaceStance;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ParticleCaster;
import model.ES.component.visuals.ParticleCaster.Facing;
import model.ES.processor.PlayerControlProc;
import model.ES.processor.holder.BoneHoldingProc;
import model.ES.processor.holder.PlanarHoldingProc;
import model.ES.processor.motion.InertiaMotionProc;
import model.ES.processor.motion.PlanarRotationProc;
import model.ES.processor.motion.PlanarThrustProc;
import model.ES.processor.shipGear.ParticleThrusterProc;
import model.ES.processor.shipGear.RotationThrusterProc;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.drawingProcessors.InertiaVisualisationProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.ParticleCasterProc;
import view.drawingProcessors.PlacingModelProc;
import view.material.MaterialManager;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import controller.Controller;
import controller.cameraManagement.ChasingCameraProc;
import controller.editor.EditorCtrl;
import controller.entityAppState.EntityDataAppState;
import controller.topdown.TopdownCtrl;

public class MainDev extends CosmoVania {

	private Controller currentAppState;
	
	public static void main(String[] args) {
		CosmoVania.main(new MainDev());
	}

	@Override
	public void simpleInitApp() {
		MaterialManager.setAssetManager(assetManager);

		stateManager.attach(new TopdownCtrl());
		stateManager.getState(TopdownCtrl.class).setEnabled(false);
		
		stateManager.attach(new EditorCtrl());
		stateManager.getState(EditorCtrl.class).setEnabled(true);
		currentAppState = stateManager.getState(EditorCtrl.class);
		
		stateManager.attach(new EntityDataAppState());
		stateManager.attach(new PlayerControlProc());
		stateManager.attach(new RotationThrusterProc());
		stateManager.attach(new PlanarRotationProc());
		stateManager.attach(new PlanarThrustProc());
		stateManager.attach(new InertiaMotionProc());
		stateManager.attach(new ModelProc());
		stateManager.attach(new PlacingModelProc());
		stateManager.attach(new BoneHoldingProc());
		stateManager.attach(new InertiaVisualisationProc());
		stateManager.attach(new ChasingCameraProc(stateManager.getState(TopdownCtrl.class).getCameraManager()));
//		stateManager.attach(new ParticleCasterProc());
		stateManager.attach(new PlanarHoldingProc());
		stateManager.attach(new ParticleThrusterProc());
		stateManager.attach(new ParticleCasterInPlaneProc());
		
		
		
		EntityData ed = stateManager.getState(EntityDataAppState.class).getEntityData();
		
		// ship
		ModelManager.entityData = ed;
		ModelManager.shipID = ed.createEntity();
		ed.setComponent(ModelManager.shipID, new PlayerControl());
		ed.setComponent(ModelManager.shipID, new PlanarStance(new Point2D(1, 1), 0, 0.5, Point3D.UNIT_Z));
		ed.setComponent(ModelManager.shipID, new PlanarWippingInertia(Point2D.ORIGIN));
		ed.setComponent(ModelManager.shipID, new PlanarMotionCapacity(3, AngleUtil.toRadians(360), 5, 100));
		ed.setComponent(ModelManager.shipID, new Model("human/adav/adav02b.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
		
		// thruster
		EntityId rotth = ed.createEntity();
		ed.setComponent(rotth, new PlanarHolding(ModelManager.shipID, new Point3D(1, 0.5, 0), -AngleUtil.toRadians(20)));
		ed.setComponent(rotth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rotth, new RotationThruster(true, AngleUtil.toRadians(5), 0, false));
		ed.setComponent(rotth, new ParticleCaster("particles/flame.png",
				2,
				2,
				0,
				0,
				false,
				200,
				80,
				0.4,
				0.1,
				new Color(1, 0.3f, 0.3f, 1),
				new Color(0.5f, 0.5f, 0.5f, 1),
				0.3,
				0.5,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false));


		// camera
		EntityId camId= ed.createEntity();
		ed.setComponent(camId, new PlanarStance(new Point2D(1, 1), 0, 20, Point3D.UNIT_Z));
		ed.setComponent(camId, new ChasingCamera(ModelManager.shipID, 3, 0, 0.5, 0.5));
		ed.setComponent(camId, new PlanarMotionCapacity(1, AngleUtil.toRadians(500), 1, 0.1));
		

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
		currentAppState.setEnabled(false);
		currentAppState = stateManager.getState(e.getControllerClass());
		currentAppState.setEnabled(true);
	}
}
