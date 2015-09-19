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
import model.ES.component.Cooldown;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlayerControl;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.motion.Dragging;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.Physic;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.collision.CollisionShape;
import model.ES.component.motion.collision.EffectOnTouch;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.CapacityActivation;
import model.ES.component.shipGear.Gun;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ParticleCaster;
import model.ES.processor.LifeTimeProc;
import model.ES.processor.RemoveProc;
import model.ES.processor.command.NeededRotationProc;
import model.ES.processor.command.NeededThrustProc;
import model.ES.processor.command.PlayerCapacityControlProc;
import model.ES.processor.command.PlayerOrthogonalThrustControlProc;
import model.ES.processor.command.PlayerRotationControlProc;
import model.ES.processor.command.PlayerThrustControlProc;
import model.ES.processor.holder.BoneHoldingProc;
import model.ES.processor.holder.PlanarHoldingProc;
import model.ES.processor.motion.DraggingProc;
import model.ES.processor.motion.VelocityApplicationProc;
import model.ES.processor.physic.PhysicForceProc;
import model.ES.processor.physic.collision.CollisionProc;
import model.ES.processor.physic.collision.CollisionResolutionProc;
import model.ES.processor.physic.collision.TouchingDestructionProc;
import model.ES.processor.physic.collision.TouchingEffectProc;
import model.ES.processor.shipGear.GunProc;
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
import view.drawingProcessors.VelocityVisualisationProc;
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
		// commands
		stateManager.attach(new PlayerRotationControlProc());
		stateManager.attach(new PlayerThrustControlProc());
//		stateManager.attach(new PlayerOrthogonalThrustControlProc());
		stateManager.attach(new RotationThrusterProc());
		stateManager.attach(new ThrusterProc());
		// forces
		stateManager.attach(new NeededRotationProc());
		stateManager.attach(new NeededThrustProc());
		stateManager.attach(new DraggingProc());
		stateManager.attach(new PhysicForceProc());
		stateManager.attach(new VelocityApplicationProc());
		// collisions
		stateManager.attach(new CollisionProc());
		stateManager.attach(new CollisionResolutionProc());
		// relations	
		stateManager.attach(new BoneHoldingProc());
		stateManager.attach(new ChasingCameraProc(stateManager.getState(TopdownCtrl.class).getCameraManager()));
		stateManager.attach(new PlanarHoldingProc());
		stateManager.attach(new ParticleThrusterProc());
		stateManager.attach(new ParticleCasterInPlaneProc());
		stateManager.attach(new PlayerCapacityControlProc());
		stateManager.attach(new GunProc());

		stateManager.attach(new ModelProc());
		stateManager.attach(new PlacingModelProc());
		stateManager.attach(new VelocityVisualisationProc());
		stateManager.attach(new TouchingEffectProc());
		stateManager.attach(new TouchingDestructionProc());

		stateManager.attach(new LifeTimeProc());
		stateManager.attach(new RemoveProc());
		
		EntityData ed = stateManager.getState(EntityDataAppState.class).getEntityData();
		
		
		// collisionatationneur
		EntityId mechantcollisionneur = ed.createEntity();
		ed.setComponent(mechantcollisionneur, new PlanarStance(new Point2D(10, 10), 0, 0, Point3D.UNIT_Z));
		ed.setComponent(mechantcollisionneur, new Model("human/adav/adav02b.mesh.xml", 0.01, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(mechantcollisionneur, new Physic(Point2D.ORIGIN, 400, new CollisionShape(2), 0.8));
		ed.setComponent(mechantcollisionneur, new Dragging(0.1));
		ed.setComponent(mechantcollisionneur, new MotionCapacity(3, AngleUtil.toRadians(360), 10));
		ed.setComponent(mechantcollisionneur, new VelocityViewing());
		ed.setComponent(mechantcollisionneur, new PlanarVelocityToApply(Point2D.ORIGIN));
		
		

		// ship
		EntityId playerShip = ed.createEntity();
		ed.setComponent(playerShip, new PlayerControl());
		ed.setComponent(playerShip, new PlanarStance(new Point2D(1, 1), 0, 0.5, Point3D.UNIT_Z));
		ed.setComponent(playerShip, new Dragging(0.05));
		ed.setComponent(playerShip, new MotionCapacity(3, AngleUtil.toRadians(360), 3));
		ed.setComponent(playerShip, new Model("human/adav/adav02b.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(playerShip, new Physic(Point2D.ORIGIN, 100, new CollisionShape(0.5), 0.8));
		ed.setComponent(playerShip, new EffectOnTouch());
		ed.setComponent(playerShip, new VelocityViewing());
		ed.setComponent(playerShip, new PlanarVelocityToApply(Point2D.ORIGIN));
		
		
		// rotation thrusters
		EntityId rotth1 = ed.createEntity();
		ed.setComponent(rotth1, new PlanarHolding(playerShip, new Point3D(0.5, 0.2, 0), -AngleUtil.toRadians(20)));
		ed.setComponent(rotth1, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rotth1, new RotationThruster(true, AngleUtil.toRadians(5), 0, false));
		ed.setComponent(rotth1, getCaster1());

		EntityId rotth2 = ed.createEntity();
		ed.setComponent(rotth2, new PlanarHolding(playerShip, new Point3D(0.5, -0.2, 0), -AngleUtil.toRadians(20)));
		ed.setComponent(rotth2, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rotth2, new RotationThruster(false, AngleUtil.toRadians(-5), 0, false));
		ed.setComponent(rotth2, getCaster1());

		// main thruster
		EntityId rearth = ed.createEntity();
		ed.setComponent(rearth, new PlanarHolding(playerShip, new Point3D(-0.7, 0, 0), AngleUtil.FLAT));
		ed.setComponent(rearth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearth, new Thruster(new Point3D(1, 0, 0), AngleUtil.toRadians(90), 0, false));
		ed.setComponent(rearth, getCaster2());

		// front thrusters
		EntityId frontleftth = ed.createEntity();
		ed.setComponent(frontleftth, new PlanarHolding(playerShip, new Point3D(0.4, 0.15, 0), AngleUtil.toRadians(20)));
		ed.setComponent(frontleftth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(frontleftth, new Thruster(new Point3D(-1, -1, 0), AngleUtil.toRadians(70), 0, true));
		ed.setComponent(frontleftth, getCaster3());

		EntityId frontrightth = ed.createEntity();
		ed.setComponent(frontrightth, new PlanarHolding(playerShip, new Point3D(0.4, -0.15, 0), AngleUtil.toRadians(-20)));
		ed.setComponent(frontrightth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(frontrightth, new Thruster(new Point3D(-1, 1, 0), AngleUtil.toRadians(70), 0, true));
		ed.setComponent(frontrightth, getCaster3());

		// lateral thrusters
		EntityId rearleftth = ed.createEntity();
		ed.setComponent(rearleftth, new PlanarHolding(playerShip, new Point3D(-0.34, 0.2, 0), AngleUtil.toRadians(110)));
		ed.setComponent(rearleftth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearleftth, new Thruster(new Point3D(1, -1.5, 0), AngleUtil.toRadians(50), 0, true));
		ed.setComponent(rearleftth, getCaster3());

		EntityId rearrightth = ed.createEntity();
		ed.setComponent(rearrightth, new PlanarHolding(playerShip, new Point3D(-0.34, -0.2, 0), AngleUtil.toRadians(-110)));
		ed.setComponent(rearrightth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearrightth, new Thruster(new Point3D(1, 1.5, 0), AngleUtil.toRadians(50), 0, true));
		ed.setComponent(rearrightth, getCaster3());

		// weapon
		EntityId weapon = ed.createEntity();
		ed.setComponent(weapon, new PlanarHolding(playerShip, new Point3D(0, 0, 0), 0));
		ed.setComponent(weapon, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(weapon, new Cooldown(0, 100));
		ed.setComponent(weapon, new CapacityActivation("gun", false));
		ed.setComponent(weapon, new PlayerControl());
		ed.setComponent(weapon, new Gun());

		// camera
		EntityId camId= ed.createEntity();
		ed.setComponent(camId, new PlanarStance(new Point2D(1, 1), 0, 20, Point3D.UNIT_Z));
		ed.setComponent(camId, new ChasingCamera(playerShip, 3, 0, 0.5, 0.5));
		ed.setComponent(camId, new MotionCapacity(1, AngleUtil.toRadians(500), 1));
		
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
