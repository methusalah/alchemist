package app;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.ES.component.Cooldown;
import model.ES.component.Naming;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlayerControl;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.senses.Sighting;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.SpaceStance;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.component.relation.AbilityTriggerList;
import model.ES.component.relation.Attackable;
import model.ES.component.relation.Parenting;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.Attrition;
import model.ES.component.shipGear.Gun;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.shipGear.Trigger;
import model.ES.component.shipGear.TriggerPersistence;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ParticleCaster;
import model.ES.processor.LifeTimeProc;
import model.ES.processor.RemoveProc;
import model.ES.processor.AI.BehaviorTreeProc;
import model.ES.processor.ability.AbilityTriggerResetProc;
import model.ES.processor.ability.GunProc;
import model.ES.processor.ability.TriggerCancelationProc;
import model.ES.processor.ability.TriggerObserverProc;
import model.ES.processor.command.NeededRotationProc;
import model.ES.processor.command.NeededThrustProc;
import model.ES.processor.command.PlayerAbilityControlProc;
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
import model.ES.processor.shipGear.LightThrusterProc;
import model.ES.processor.shipGear.ParticleThrusterProc;
import model.ES.processor.shipGear.RotationThrusterProc;
import model.ES.processor.shipGear.ThrusterProc;
import model.ES.richData.CollisionShape;
import model.ES.richData.PhysicStat;
import model.ES.serial.EntityBlueprint;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
		
		// ability
		stateManager.attach(new PlayerAbilityControlProc());
		stateManager.attach(new BehaviorTreeProc());
		stateManager.attach(new TriggerObserverProc());
		stateManager.attach(new AbilityTriggerResetProc());

		stateManager.attach(new TriggerCancelationProc());
		stateManager.attach(new GunProc());

		stateManager.attach(new DamagingProc());
		stateManager.attach(new AttritionProc());

		stateManager.attach(new SightProc());
		

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
		ed.setComponent(o1, new Model("human/adav/adav02b.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
		ed.setComponent(o1, new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 200, new CollisionShape(1), 0.8), null));
		ed.setComponent(o1, new Dragging(0.1));
		ed.setComponent(o1, new MotionCapacity(2, AngleUtil.toRadians(300), 3, 0.1, 0.1));
		ed.setComponent(o1, new PlanarVelocityToApply(Point2D.ORIGIN));
		ed.setComponent(o1, new Attrition(30, 30));
		ed.setComponent(o1, new Sighting(8, AngleUtil.toRadians(100), new ArrayList<>()));
		ed.setComponent(o1, new AbilityTriggerList(new HashMap<>()));

		attachThruster(ed, o1);


		// weapon
		EntityId o1weap = ed.createEntity();
		ed.setComponent(o1weap, new Parenting(o1));
		ed.setComponent(o1weap, new PlanarHolding(new Point3D(0, -0.3, 0), 0));
		ed.setComponent(o1weap, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(o1weap, new Cooldown(0, 1000));
		ed.setComponent(o1weap, new TriggerPersistence(300, 0));
		ed.setComponent(o1weap, new Trigger("gun", false));
		ed.setComponent(o1weap, new Gun());

//		// ship
//		EntityId playerShip = ed.createEntity();
//		ed.setComponent(playerShip, new PlayerControl());
//		ed.setComponent(playerShip, new PlanarStance(new Point2D(1, 1), 0, 0.5, Point3D.UNIT_Z));
//		ed.setComponent(playerShip, new Dragging(0.05));
//		ed.setComponent(playerShip, new MotionCapacity(3, AngleUtil.toRadians(360), 3, 1.5, 1.5));
//		ed.setComponent(playerShip, new Model("human/adav/adav02b.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
//		ed.setComponent(playerShip, new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 100, new CollisionShape(0.5), 0.8), null));
//		ed.setComponent(playerShip, new EffectOnTouch());
//		ed.setComponent(playerShip, new VelocityViewing());
//		ed.setComponent(playerShip, new PlanarVelocityToApply(Point2D.ORIGIN));
//		ed.setComponent(playerShip, new Attackable());
//		ed.setComponent(playerShip, new AbilityTriggerList(new HashMap<>()));
//
//		attachThruster(ed, playerShip);
//
//		// weapon
//		EntityId weaponLeft = ed.createEntity();
//		ed.setComponent(weaponLeft, new PlanarHolding(playerShip, new Point3D(0, 0.3, 0), 0));
//		ed.setComponent(weaponLeft, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
//		ed.setComponent(weaponLeft, new Cooldown(0, 100));
//		ed.setComponent(weaponLeft, new Trigger(playerShip, "gun", false));
//		ed.setComponent(weaponLeft, new Gun());
//
//		// weapon
//		EntityId weaponRight = ed.createEntity();
//		ed.setComponent(weaponRight, new PlanarHolding(playerShip, new Point3D(0, -0.3, 0), 0));
//		ed.setComponent(weaponRight, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
//		ed.setComponent(weaponRight, new Cooldown(0, 100));
//		ed.setComponent(weaponRight, new Trigger(playerShip, "gun", false));
//		ed.setComponent(weaponRight, new Gun());
//
//		// light
//		EntityId frontLight = ed.createEntity();
//		ed.setComponent(frontLight, new PlanarHolding(playerShip, new Point3D(1, 0, 0), 0));
//		ed.setComponent(frontLight, new SpaceStance(Point3D.ORIGIN, Point3D.ORIGIN));
//		ed.setComponent(frontLight, new Lighting(Color.white, 4, 6, AngleUtil.toRadians(30), AngleUtil.toRadians(40), false, 1));
//
//		// camera
//		EntityId camId= ed.createEntity();
//		ed.setComponent(camId, new PlanarStance(new Point2D(1, 1), 0, 30, Point3D.UNIT_Z));
//		ed.setComponent(camId, new ChasingCamera(playerShip, 3, 0, 0.5, 0.5));
//		ed.setComponent(camId, new MotionCapacity(1, AngleUtil.toRadians(500), 1, 1, 1));

		
		
//		EventManager.register(this);
//		
//		EntityBlueprint weaponLeftBP = new EntityBlueprint();
//		weaponLeftBP.comps.add(new Naming("weapon left"));
//		weaponLeftBP.comps.add(new PlanarHolding(new Point3D(0, 0.3, 0), 0));
//		weaponLeftBP.comps.add(new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
//		weaponLeftBP.comps.add(new Cooldown(0, 100));
//		weaponLeftBP.comps.add(new Trigger("gun", false));
//		weaponLeftBP.comps.add(new Gun());
//
//		EntityBlueprint weaponRightBP = new EntityBlueprint();
//		weaponRightBP.comps.add(new Naming("weapon right"));
//		weaponRightBP.comps.add(new PlanarHolding(new Point3D(0, 0.3, 0), 0));
//		weaponRightBP.comps.add(new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
//		weaponRightBP.comps.add(new Cooldown(0, 100));
//		weaponRightBP.comps.add(new Trigger("gun", false));
//		weaponRightBP.comps.add(new Gun());
//		
////		EntityBlueprint frontLightBP = new EntityBlueprint();
////		frontLightBP.comps.add(new Naming("front light"));
////		frontLightBP.comps.add(new PlanarHolding(null, new Point3D(1, 0, 0), 0));
////		frontLightBP.comps.add(new SpaceStance(Point3D.ORIGIN, Point3D.ORIGIN));
////		frontLightBP.comps.add(new Lighting(Color.white, 4, 6, AngleUtil.toRadians(30), AngleUtil.toRadians(40), false, 1));
//
//		// camera
//		EntityBlueprint camBP = new EntityBlueprint();
//		camBP.comps.add(new Naming("camera"));
//		camBP.comps.add(new PlanarStance(new Point2D(1, 1), 0, 30, Point3D.UNIT_Z));
//		camBP.comps.add(new ChasingCamera(3, 0, 0.5, 0.5));
//		camBP.comps.add(new MotionCapacity(1, AngleUtil.toRadians(500), 1, 1, 1));
//		
//		EntityBlueprint shipBP = new EntityBlueprint();
//		shipBP.comps.add(new Naming("player ship"));
//		shipBP.comps.add(new PlayerControl());
//		shipBP.comps.add(new PlanarStance(new Point2D(1, 1), 0, 0.5, Point3D.UNIT_Z));
//		shipBP.comps.add(new Dragging(0.05));
//		shipBP.comps.add(new MotionCapacity(3, AngleUtil.toRadians(360), 3, 1.5, 1.5));
//		shipBP.comps.add(new Model("human/adav/adav02b.mesh.xml", 0.0025, 0, AngleUtil.toRadians(-90), 0));
//		shipBP.comps.add(new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 100, new CollisionShape(0.5), 0.8), null));
//		shipBP.comps.add(new EffectOnTouch());
//		shipBP.comps.add(new VelocityViewing());
//		shipBP.comps.add(new PlanarVelocityToApply(Point2D.ORIGIN));
//		shipBP.comps.add(new Attackable());
//		shipBP.comps.add(new AbilityTriggerList(new HashMap<>()));
//		shipBP.children.add(weaponLeftBP);
//		shipBP.children.add(weaponRightBP);
////		shipBP.children.add(frontLightBP);
//		shipBP.children.add(camBP);
//	
//		
//		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
//		mapper.enable(SerializationFeature.INDENT_OUTPUT);
//		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//		try {
//			mapper.writeValue(new File("test.bp"), shipBP);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		

		
		
		
		
		
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		EntityBlueprint bp = null;
		try {
			bp = mapper.readValue(new File("test.bp"), EntityBlueprint.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bp.create(ed, null);
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
	
	private void attachThruster(EntityData ed, EntityId eid){
		// rotation thrusters
		EntityId rotth1 = ed.createEntity();
		ed.setComponent(rotth1, new Parenting(eid));
		ed.setComponent(rotth1, new PlanarHolding(new Point3D(0.5, 0.2, 0), -AngleUtil.toRadians(20)));
		ed.setComponent(rotth1, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rotth1, new RotationThruster(true, AngleUtil.toRadians(5), 0, false));
		ed.setComponent(rotth1, getCaster1());

		EntityId rotth2 = ed.createEntity();
		ed.setComponent(rotth2, new Parenting(eid));
		ed.setComponent(rotth2, new PlanarHolding(new Point3D(0.5, -0.2, 0), -AngleUtil.toRadians(20)));
		ed.setComponent(rotth2, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rotth2, new RotationThruster(false, AngleUtil.toRadians(-5), 0, false));
		ed.setComponent(rotth2, getCaster1());

		// main thruster
		EntityId rearth = ed.createEntity();
		ed.setComponent(rearth, new Parenting(eid));
		ed.setComponent(rearth, new PlanarHolding(new Point3D(-0.7, 0, 0), AngleUtil.FLAT));
		ed.setComponent(rearth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearth, new Thruster(new Point3D(1, 0, 0), AngleUtil.toRadians(90), 0, false));
		ed.setComponent(rearth, getCaster2());
		ed.setComponent(rearth, new Lighting(Color.orange, 3, 6, AngleUtil.toRadians(10), AngleUtil.toRadians(60), false, 0));

		// front thrusters
		EntityId frontleftth = ed.createEntity();
		ed.setComponent(frontleftth, new Parenting(eid));
		ed.setComponent(frontleftth, new PlanarHolding(new Point3D(0.4, 0.15, 0), AngleUtil.toRadians(20)));
		ed.setComponent(frontleftth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(frontleftth, new Thruster(new Point3D(-1, -1, 0), AngleUtil.toRadians(70), 0, true));
		ed.setComponent(frontleftth, getCaster3());

		EntityId frontrightth = ed.createEntity();
		ed.setComponent(frontrightth, new Parenting(eid));
		ed.setComponent(frontrightth, new PlanarHolding(new Point3D(0.4, -0.15, 0), AngleUtil.toRadians(-20)));
		ed.setComponent(frontrightth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(frontrightth, new Thruster(new Point3D(-1, 1, 0), AngleUtil.toRadians(70), 0, true));
		ed.setComponent(frontrightth, getCaster3());

		// lateral thrusters
		EntityId rearleftth = ed.createEntity();
		ed.setComponent(rearleftth, new Parenting(eid));
		ed.setComponent(rearleftth, new PlanarHolding(new Point3D(-0.34, 0.2, 0), AngleUtil.toRadians(110)));
		ed.setComponent(rearleftth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearleftth, new Thruster(new Point3D(1, -1.5, 0), AngleUtil.toRadians(50), 0, true));
		ed.setComponent(rearleftth, getCaster3());

		EntityId rearrightth = ed.createEntity();
		ed.setComponent(rearrightth, new Parenting(eid));
		ed.setComponent(rearrightth, new PlanarHolding(new Point3D(-0.34, -0.2, 0), AngleUtil.toRadians(-110)));
		ed.setComponent(rearrightth, new PlanarStance(Point2D.ORIGIN, 0, 0, Point3D.UNIT_Z));
		ed.setComponent(rearrightth, new Thruster(new Point3D(1, 1.5, 0), AngleUtil.toRadians(50), 0, true));
		ed.setComponent(rearrightth, getCaster3());
	}

}
