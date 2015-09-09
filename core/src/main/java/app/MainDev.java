package app;

import java.awt.Color;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import controller.Controller;
import controller.cameraManagement.ChasingCameraProc;
import controller.editor.EditorCtrl;
import controller.entityAppState.EntityDataAppState;
import controller.topdown.TopdownCtrl;
import model.ModelManager;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.planarMotion.PlanarMotionCapacity;
import model.ES.component.planarMotion.PlanarStance;
import model.ES.component.planarMotion.PlanarWippingInertia;
import model.ES.component.planarMotion.PlayerControl;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ParticleCaster;
import model.ES.processor.PlayerControlProc;
import model.ES.processor.holder.BoneHoldingProc;
import model.ES.processor.holder.PlanarHoldingProc;
import model.ES.processor.motion.InertiaMotionProc;
import model.ES.processor.motion.PlanarRotationProc;
import model.ES.processor.motion.PlanarThrustProc;
import model.ES.processor.shipGear.ParticleThrusterProc;
import model.ES.processor.shipGear.RotationThrusterProc;
import model.ES.processor.shipGear.ThrusterProc;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.PlacingModelProc;
import view.material.MaterialManager;

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
		stateManager.attach(new ThrusterProc());
		stateManager.attach(new PlanarRotationProc());
		stateManager.attach(new PlanarThrustProc());
//		stateManager.attach(new PlanarOrthogonalThrustProc());
		stateManager.attach(new InertiaMotionProc());
		stateManager.attach(new ModelProc());
		stateManager.attach(new PlacingModelProc());
		stateManager.attach(new BoneHoldingProc());
//		stateManager.attach(new InertiaVisualisationProc());
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
		
		// rotation thruster
		EntityId rotth1 = ed.createEntity();
		ed.setComponent(rotth1, new PlanarHolding(ModelManager.shipID, new Point3D(0.5, 0.2, 0), -AngleUtil.toRadians(20)));
		ed.setComponent(rotth1, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rotth1, new RotationThruster(true, AngleUtil.toRadians(5), 0, false));
		ed.setComponent(rotth1, getCaster1());

		EntityId rotth2 = ed.createEntity();
		ed.setComponent(rotth2, new PlanarHolding(ModelManager.shipID, new Point3D(0.5, -0.2, 0), -AngleUtil.toRadians(20)));
		ed.setComponent(rotth2, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rotth2, new RotationThruster(false, AngleUtil.toRadians(-5), 0, false));
		ed.setComponent(rotth2, getCaster1());

		EntityId rearth = ed.createEntity();
		ed.setComponent(rearth, new PlanarHolding(ModelManager.shipID, new Point3D(-0.7, 0, 0), AngleUtil.FLAT));
		ed.setComponent(rearth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearth, new Thruster(new Point3D(1, 0, 0), AngleUtil.toRadians(90), 0, false));
		ed.setComponent(rearth, getCaster2());

		EntityId frontleftth = ed.createEntity();
		ed.setComponent(frontleftth, new PlanarHolding(ModelManager.shipID, new Point3D(0.4, 0.15, 0), AngleUtil.toRadians(20)));
		ed.setComponent(frontleftth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(frontleftth, new Thruster(new Point3D(-1, -1, 0), AngleUtil.toRadians(70), 0, true));
		ed.setComponent(frontleftth, getCaster3());

		EntityId frontrightth = ed.createEntity();
		ed.setComponent(frontrightth, new PlanarHolding(ModelManager.shipID, new Point3D(0.4, -0.15, 0), AngleUtil.toRadians(-20)));
		ed.setComponent(frontrightth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(frontrightth, new Thruster(new Point3D(-1, 1, 0), AngleUtil.toRadians(70), 0, true));
		ed.setComponent(frontrightth, getCaster3());

		EntityId rearleftth = ed.createEntity();
		ed.setComponent(rearleftth, new PlanarHolding(ModelManager.shipID, new Point3D(-0.34, 0.2, 0), AngleUtil.toRadians(110)));
		ed.setComponent(rearleftth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearleftth, new Thruster(new Point3D(1, -1.5, 0), AngleUtil.toRadians(50), 0, true));
		ed.setComponent(rearleftth, getCaster3());

		EntityId rearrightth = ed.createEntity();
		ed.setComponent(rearrightth, new PlanarHolding(ModelManager.shipID, new Point3D(-0.34, -0.2, 0), AngleUtil.toRadians(-110)));
		ed.setComponent(rearrightth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearrightth, new Thruster(new Point3D(1, 1.5, 0), AngleUtil.toRadians(50), 0, true));
		ed.setComponent(rearrightth, getCaster3());


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
	
	private ParticleCaster getCaster1(){
		return new ParticleCaster("particles/flame.png",
				2,
				2,
				1,
				0.1,
				false,
				200,
				80,
				0.1,
				0.1,
				new Color(255, 255, 255, 255),
				new Color(255, 255, 255, 0),
				0.1,
				0.1,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false);
	}
	
	private ParticleCaster getCaster2(){
		return new ParticleCaster("particles/flame.png",
				2,
				2,
				5,
				0,
				false,
				300,
				100,
				0.4,
				0.1,
				new Color(1, 0.3f, 0.3f, 1),
				new Color(0.5f, 0.5f, 0.5f, 1),
				0.1,
				0.2,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false);
	}
	private ParticleCaster getCaster3(){
		return new ParticleCaster("particles/flame.png",
				2,
				2,
				4,
				0,
				false,
				300,
				100,
				0.1,
				0.05,
				new Color(1, 0.3f, 0.3f, 1),
				new Color(0.5f, 0.5f, 0.5f, 1),
				0.05,
				0.1,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false);
	}

}
