package app;

import java.awt.Color;
import java.util.ArrayList;

import model.ES.component.Cooldown;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlayerControl;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.SpaceStance;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.senses.Sighting;
import model.ES.component.shipGear.Attrition;
import model.ES.component.shipGear.CapacityActivation;
import model.ES.component.shipGear.Gun;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ParticleCaster;
import model.ES.processor.LifeTimeProc;
import model.ES.processor.RemoveProc;
import model.ES.processor.AI.AttackOnSightProc;
import model.ES.processor.command.NeededRotationProc;
import model.ES.processor.command.NeededThrustProc;
import model.ES.processor.command.PlayerCapacityControlProc;
import model.ES.processor.command.PlayerRotationControlProc;
import model.ES.processor.command.PlayerThrustControlProc;
import model.ES.processor.holder.BoneHoldingProc;
import model.ES.processor.holder.PlanarHoldingProc;
import model.ES.processor.interaction.DamageOnTouchProc;
import model.ES.processor.interaction.DamagingProc;
import model.ES.processor.interaction.DestroyedOnTouchProc;
import model.ES.processor.interaction.EffectOnTouchProc;
import model.ES.processor.interaction.ShockwaveOnTouchProc;
import model.ES.processor.motion.VelocityApplicationProc;
import model.ES.processor.motion.physic.CollisionProc;
import model.ES.processor.motion.physic.CollisionResolutionProc;
import model.ES.processor.motion.physic.DraggingProc;
import model.ES.processor.motion.physic.PhysicForceProc;
import model.ES.processor.senses.SightProc;
import model.ES.processor.shipGear.AttritionProc;
import model.ES.processor.shipGear.GunProc;
import model.ES.processor.shipGear.LightThrusterProc;
import model.ES.processor.shipGear.ParticleThrusterProc;
import model.ES.processor.shipGear.RotationThrusterProc;
import model.ES.processor.shipGear.ThrusterProc;
import model.ES.richData.CollisionShape;
import model.ES.richData.PhysicStat;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.drawingProcessors.LightProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.PlacingModelProc;
import view.drawingProcessors.VelocityVisualisationProc;
import view.material.MaterialManager;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import controller.Controller;
import controller.cameraManagement.ChasingCameraProc;
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
		stateManager.getState(TopdownCtrl.class).setEnabled(true);
		currentAppState = stateManager.getState(TopdownCtrl.class);
		
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
		stateManager.attach(new LightThrusterProc());
		stateManager.attach(new ParticleCasterInPlaneProc());
		stateManager.attach(new PlayerCapacityControlProc());
		stateManager.attach(new GunProc());
		stateManager.attach(new DamagingProc());
		stateManager.attach(new AttritionProc());

		stateManager.attach(new SightProc());
		stateManager.attach(new AttackOnSightProc());
		

		stateManager.attach(new ModelProc());
		stateManager.attach(new PlacingModelProc());
		stateManager.attach(new LightProc());
		stateManager.attach(new VelocityVisualisationProc());
		stateManager.attach(new EffectOnTouchProc());
		stateManager.attach(new DamageOnTouchProc());
		stateManager.attach(new DestroyedOnTouchProc());
		stateManager.attach(new ShockwaveOnTouchProc());
		
		stateManager.attach(new LifeTimeProc());
		stateManager.attach(new RemoveProc());
		
		EntityData ed = stateManager.getState(EntityDataAppState.class).getEntityData();
		
		// sun light
		EntityId sun = ed.createEntity();
		ed.setComponent(sun, new Lighting(Color.white, 1.5, Double.POSITIVE_INFINITY, 0, 0, true, 1));
		ed.setComponent(sun, new SpaceStance(Point3D.ORIGIN, new Point3D(-1, -1, -1)));
		

		// collisioner
		EntityId o1 = ed.createEntity();
		ed.setComponent(o1, new PlanarStance(new Point2D(10, 10), 0, 0, Point3D.UNIT_Z));
		ed.setComponent(o1, new Model("human/adav/adav02b.mesh.xml", 0.005, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(o1, new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 200, new CollisionShape(1), 0.8), null));
		ed.setComponent(o1, new Dragging(0.1));
		ed.setComponent(o1, new MotionCapacity(2, AngleUtil.toRadians(200), 3));
		ed.setComponent(o1, new PlanarVelocityToApply(Point2D.ORIGIN));
		ed.setComponent(o1, new Attrition(30, 30));
		ed.setComponent(o1, new Sighting(8, AngleUtil.toRadians(60), new ArrayList<>()));

		EntityId o2 = ed.createEntity();
		ed.setComponent(o2, new PlanarStance(new Point2D(10, 8), 0, 0, Point3D.UNIT_Z));
		ed.setComponent(o2, new Model("human/adav/adav02b.mesh.xml", 0.005, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(o2, new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 200, new CollisionShape(1), 0.8), null));
		ed.setComponent(o2, new Dragging(0.1));
		ed.setComponent(o2, new MotionCapacity(2, AngleUtil.toRadians(200), 3));
		ed.setComponent(o2, new PlanarVelocityToApply(Point2D.ORIGIN));
		ed.setComponent(o2, new Attrition(30, 30));
		ed.setComponent(o2, new Sighting(8, AngleUtil.toRadians(60), new ArrayList<>()));

		EntityId o3 = ed.createEntity();
		ed.setComponent(o3, new PlanarStance(new Point2D(8, 10), 0, 0, Point3D.UNIT_Z));
		ed.setComponent(o3, new Model("human/adav/adav02b.mesh.xml", 0.005, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(o3, new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 200, new CollisionShape(1), 0.8), null));
		ed.setComponent(o3, new Dragging(0.1));
		ed.setComponent(o3, new MotionCapacity(2, AngleUtil.toRadians(200), 3));
		ed.setComponent(o3, new PlanarVelocityToApply(Point2D.ORIGIN));
		ed.setComponent(o3, new Attrition(30, 30));
		ed.setComponent(o3, new Sighting(8, AngleUtil.toRadians(60), new ArrayList<>()));

		EntityId o4 = ed.createEntity();
		ed.setComponent(o4, new PlanarStance(new Point2D(12, 12), 0, 0, Point3D.UNIT_Z));
		ed.setComponent(o4, new Model("human/adav/adav02b.mesh.xml", 0.01, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(o4, new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 400, new CollisionShape(2), 0.8), null));
		ed.setComponent(o4, new Dragging(0.1));
		ed.setComponent(o4, new MotionCapacity(2, AngleUtil.toRadians(200), 3));
		ed.setComponent(o4, new PlanarVelocityToApply(Point2D.ORIGIN));
		ed.setComponent(o4, new Attrition(60, 60));
		ed.setComponent(o4, new Sighting(10, AngleUtil.toRadians(60), new ArrayList<>()));

		// ship
		EntityId playerShip = ed.createEntity();
		ed.setComponent(playerShip, new PlayerControl());
		ed.setComponent(playerShip, new PlanarStance(new Point2D(1, 1), 0, 0.5, Point3D.UNIT_Z));
		ed.setComponent(playerShip, new Dragging(0.05));
		ed.setComponent(playerShip, new MotionCapacity(3, AngleUtil.toRadians(360), 3));
		ed.setComponent(playerShip, new Model("human/adav/adav02b.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(playerShip, new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 100, new CollisionShape(0.5), 0.8), null));
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
		ed.setComponent(rearth, new Lighting(Color.orange, 3, 6, AngleUtil.toRadians(10), AngleUtil.toRadians(60), false, 0));

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
		EntityId weaponLeft = ed.createEntity();
		ed.setComponent(weaponLeft, new PlanarHolding(playerShip, new Point3D(0, 0.3, 0), 0));
		ed.setComponent(weaponLeft, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(weaponLeft, new Cooldown(0, 100));
		ed.setComponent(weaponLeft, new CapacityActivation("gun", false));
		ed.setComponent(weaponLeft, new PlayerControl());
		ed.setComponent(weaponLeft, new Gun(playerShip));

		// weapon
		EntityId weaponRight = ed.createEntity();
		ed.setComponent(weaponRight, new PlanarHolding(playerShip, new Point3D(0, -0.3, 0), 0));
		ed.setComponent(weaponRight, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(weaponRight, new Cooldown(0, 100));
		ed.setComponent(weaponRight, new CapacityActivation("gun", false));
		ed.setComponent(weaponRight, new PlayerControl());
		ed.setComponent(weaponRight, new Gun(playerShip));

		// light
		EntityId frontLight = ed.createEntity();
		ed.setComponent(frontLight, new PlanarHolding(playerShip, new Point3D(1, 0, 0), 0));
		ed.setComponent(frontLight, new SpaceStance(Point3D.ORIGIN, Point3D.ORIGIN));
		ed.setComponent(frontLight, new Lighting(Color.white, 4, 6, AngleUtil.toRadians(30), AngleUtil.toRadians(40), false, 1));

		// camera
		EntityId camId= ed.createEntity();
		ed.setComponent(camId, new PlanarStance(new Point2D(1, 1), 0, 30, Point3D.UNIT_Z));
		ed.setComponent(camId, new ChasingCamera(playerShip, 3, 0, 0.5, 0.5));
		ed.setComponent(camId, new MotionCapacity(1, AngleUtil.toRadians(500), 1));
		
		EventManager.register(this);
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
