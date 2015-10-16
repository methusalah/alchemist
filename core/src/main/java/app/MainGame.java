package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import model.ES.component.Cooldown;
import model.ES.component.Naming;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlanarNeededThrust;
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
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.Attrition;
import model.ES.component.shipGear.Boost;
import model.ES.component.shipGear.ProjectileLauncher;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.shipGear.Trigger;
import model.ES.component.shipGear.TriggerRepeater;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCasting;
import model.ES.component.visuals.Sprite;
import model.ES.processor.LifeTimeProc;
import model.ES.processor.RemoveProc;
import model.ES.processor.AI.BehaviorTreeProc;
import model.ES.processor.ability.AbilityTriggerResetProc;
import model.ES.processor.ability.ProjectileLauncherProc;
import model.ES.processor.ability.TriggerCancelationProc;
import model.ES.processor.ability.TriggerObserverProc;
import model.ES.processor.ability.TriggerRepeaterProc;
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
import model.ES.richData.ColorData;
import model.ES.richData.ParticleCaster;
import model.ES.richData.PhysicStat;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintCreator;
import model.ES.serial.BlueprintLibrary;
import util.LogUtil;
import util.event.AppStateChangeEvent;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;
import util.math.Fraction;
import util.math.RandomUtil;
import view.drawingProcessors.CameraPlacingProc;
import view.drawingProcessors.LightProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.ModelPlacingProc;
import view.drawingProcessors.VelocityVisualisationProc;
import view.material.MaterialManager;

import com.google.common.eventbus.Subscribe;
import com.jme3.system.Annotations.Destructive;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;

import controller.Controller;
import controller.ECS.EntityDataAppState;
import controller.ECS.EntitySystem;
import controller.cameraManagement.ChasingCameraProc;
import controller.topdown.TopdownCtrl;

public class MainGame extends CosmoVania {
	private Controller currentAppState;

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
		EntityData ed = new DefaultEntityData();
		BlueprintCreator.setEntityData(ed);
		
		serialiseBluePrints();
		BlueprintCreator.create("player ship", null);
		int zoneWidth = 50;
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		BlueprintCreator.create("sun", null);
		
		for(int i = 0; i < 50; i++){
			EntityId eid = BlueprintCreator.create("asteroid", null);
			Point2D coord = new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth);
			Angle a = new Angle(RandomUtil.next()*AngleUtil.FULL);
			ed.setComponent(eid, new PlanarStance(coord, a, 0.5, Point3D.UNIT_Z));
			
			double scale = RandomUtil.between(0.5, 3);
			ed.setComponent(eid, new Physic(Point2D.ORIGIN, new PhysicStat("asteroid", 200*scale, new CollisionShape(2*scale), new Fraction(0.5)), null));
			ed.setComponent(eid, new Model("rock0"+(RandomUtil.nextInt(5)+1)+".obj", scale, new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT)), new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT)), new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT))));
			
			ed.setComponent(eid, new ModelRotation(RandomUtil.between(50, 200), 0, 0));
		}

		
		stateManager.attach(new TopdownCtrl());
		stateManager.getState(TopdownCtrl.class).setEnabled(true);
		currentAppState = stateManager.getState(TopdownCtrl.class);
		
		stateManager.attach(new EntitySystem(ed));
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
				new ColorData(255, 255, 255, 255),
				new ColorData(255, 255, 255, 0),
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
				new ColorData(1, 0.3f, 0.3f, 1),
				new ColorData(0.5f, 0.5f, 0.5f, 1),
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
				new ColorData(1, 0.3f, 0.3f, 1),
				new ColorData(0.5f, 0.5f, 0.5f, 1),
				0.05,
				0.1,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false);
	}
	
	private void serialiseBluePrints(){
		Blueprint bp;
		
		// sun light
		bp = new Blueprint("sun");
		bp.add(new Naming("Sun"));
		bp.add(new Lighting(new ColorData(255, 255, 255, 255), 1.5, Double.POSITIVE_INFINITY, 0, 0, true, new Fraction(1)));
		bp.add(new SpaceStance(Point3D.ORIGIN, new Point3D(-1, -1, -3)));
		BlueprintLibrary.save(bp);

		// rotation thrusters
		bp = new Blueprint("rotation thruster 1");
		bp.add(new Naming("rotation thruster 1"));
		bp.add(new PlanarHolding(new Point3D(0.5, 0.2, 0), new Angle(-AngleUtil.toRadians(20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new RotationThruster(true, AngleUtil.toRadians(5), 0, false));
		bp.add(new ParticleCasting(getCaster1(), getCaster1().perSecond));
		BlueprintLibrary.save(bp);

		bp = new Blueprint("rotation thruster 2");
		bp.add(new Naming("rotation thruster 2"));
		bp.add(new PlanarHolding(new Point3D(0.5, -0.2, 0), new Angle(-AngleUtil.toRadians(20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new RotationThruster(false, AngleUtil.toRadians(-5), 0, false));
		bp.add(new ParticleCasting(getCaster1(), getCaster1().perSecond));
		BlueprintLibrary.save(bp);

		// main thruster
		bp = new Blueprint("rear thruster");
		bp.add(new Naming("rear thruster"));
		bp.add(new PlanarHolding(new Point3D(-0.7, 0, 0), new Angle(AngleUtil.FLAT)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(1, 0, 0), new Angle(AngleUtil.toRadians(90)), new Fraction(0), false));
		bp.add(new ParticleCasting(getCaster2(), getCaster2().perSecond));
		bp.add(new Lighting(new ColorData(255, 255, 150, 150), 3, 6, AngleUtil.toRadians(10), AngleUtil.toRadians(60), false, new Fraction(0)));
		BlueprintLibrary.save(bp);

		// front thrusters
		bp = new Blueprint("frontal left thruster");
		bp.add(new Naming("frontal left thruster"));
		bp.add(new PlanarHolding(new Point3D(0.4, 0.15, 0), new Angle(AngleUtil.toRadians(20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(-1, -1, 0), new Angle(AngleUtil.toRadians(70)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		BlueprintLibrary.save(bp);

		bp = new Blueprint("frontal right thruster");
		bp.add(new Naming("frontal right thruster"));
		bp.add(new PlanarHolding(new Point3D(0.4, -0.15, 0), new Angle(AngleUtil.toRadians(-20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(-1, 1, 0), new Angle(AngleUtil.toRadians(70)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		BlueprintLibrary.save(bp);

		// lateral thrusters
		bp = new Blueprint("side left thruster");
		bp.add(new Naming("side left thruster"));
		bp.add(new PlanarHolding(new Point3D(-0.34, 0.2, 0), new Angle(AngleUtil.toRadians(110))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(1, -1.5, 0), new Angle(AngleUtil.toRadians(50)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		BlueprintLibrary.save(bp);

		bp = new Blueprint("side right thruster");
		bp.add(new Naming("side right thruster"));
		bp.add(new PlanarHolding(new Point3D(-0.34, -0.2, 0), new Angle(AngleUtil.toRadians(-110))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(1, 1.5, 0), new Angle(AngleUtil.toRadians(50)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		BlueprintLibrary.save(bp);
		
		// enemy
		bp = new Blueprint("enemy");
		bp.add(new Naming("enemy"));
		bp.add(new PlanarStance(new Point2D(10, 10), new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Model("human/adav/adav02b.mesh.xml", 0.0025, new Angle(0), new Angle(AngleUtil.toRadians(-90)), new Angle(0)));
		bp.add(new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 100, new CollisionShape(1), new Fraction(0.8)), null));
		bp.add(new Dragging(0.5));
		bp.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		bp.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		bp.add(new Attrition(30, 30));
		bp.add(new Sighting(8, AngleUtil.toRadians(100), new ArrayList<>()));
		bp.add(new AbilityTriggerList(new HashMap<>()));
		bp.add("enemy weapon");
		bp.add("rear thruster");
		bp.add("frontal left thruster");
		bp.add("frontal right thruster");
		bp.add("side left thruster");
		bp.add("side right thruster");
		BlueprintLibrary.save(bp);
		
		// enemy weapon
		bp = new Blueprint("enemy weapon");
		bp.add(new Naming("enemy weapon"));
		bp.add(new PlanarHolding(new Point3D(0, -0.3, 0), new Angle(0)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Cooldown(0, 1000));
		bp.add(new TriggerRepeater(300, 60, 10, 0, 0));
		bp.add(new Trigger("gun", false));
		bp.add(new ProjectileLauncher(new Fraction(0.97)));
		BlueprintLibrary.save(bp);

		// player weapons
		bp = new Blueprint("weapon left");
		bp.add(new Naming("weapon left"));
		bp.add(new PlanarHolding(new Point3D(0, 0.3, 0), new Angle(0)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Cooldown(0, 100));
		bp.add(new Trigger("gun", false));
		bp.add(new ProjectileLauncher(new Fraction(0.995)));
		BlueprintLibrary.save(bp);

		bp = new Blueprint("weapon right");
		bp.add(new Naming("weapon right"));
		bp.add(new PlanarHolding(new Point3D(0, -0.3, 0), new Angle(0)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Cooldown(0, 100));
		bp.add(new Trigger("gun", false));
		bp.add(new ProjectileLauncher(new Fraction(0.995)));
		BlueprintLibrary.save(bp);
		
		// front light
		bp = new Blueprint("front light");
		bp.add(new Naming("front light"));
		bp.add(new PlanarHolding(new Point3D(1, 0, 0), new Angle(0)));
		bp.add(new SpaceStance(Point3D.ORIGIN, Point3D.ORIGIN));
		bp.add(new Lighting(new ColorData(255, 255, 255, 255), 4, 6, AngleUtil.toRadians(30), AngleUtil.toRadians(40), false, new Fraction(1)));
		BlueprintLibrary.save(bp);

		// nebula
		bp = new Blueprint("nebula");
		bp.add(new Naming("nebula"));
		bp.add(new PlanarStance());
		bp.add(new Sprite("red_nebula.jpg", 100));
		bp.add(new PlanarHolding(new Point3D(0, 0, -30), new Angle(0)));
		BlueprintLibrary.save(bp);

		// camera
		bp = new Blueprint("camera");
		bp.add(new Naming("camera"));
		bp.add(new PlanarStance(new Point2D(1, 1), new Angle(0), 25, Point3D.UNIT_Z));
		bp.add(new ChasingCamera(Double.MAX_VALUE, 0, 5, 5, Point3D.ORIGIN, Point3D.ORIGIN));
		bp.add(new MotionCapacity(AngleUtil.toRadians(500), 1, 1, 1));
		bp.add("nebula");
		BlueprintLibrary.save(bp);
		
		// boost
		bp = new Blueprint("boost");
		bp.add(new Naming("boost"));
		bp.add(new Boost(120));
		bp.add(new Trigger("boost", false));
		BlueprintLibrary.save(bp);
		
		bp = new Blueprint("player ship");
		bp.add(new Naming("player ship"));
		bp.add(new PlayerControl());
		bp.add(new PlanarStance(new Point2D(1, 1), new Angle(0), 0.5, Point3D.UNIT_Z));
		bp.add(new Dragging(0.5));
		bp.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		bp.add(new Model("human/adav/adav02b.mesh.xml", 0.0025, new Angle(0), new Angle(-AngleUtil.RIGHT), new Angle(0)));
		bp.add(new Physic(Point2D.ORIGIN, new PhysicStat("Ship", 100, new CollisionShape(0.5), new Fraction(0.8)), null));
		bp.add(new EffectOnTouch());
		bp.add(new VelocityViewing());
		bp.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		bp.add(new PlanarNeededThrust(Point2D.ORIGIN));
		bp.add(new Attackable());
		bp.add(new AbilityTriggerList(new HashMap<>()));
		bp.add("weapon left");
		bp.add("weapon right");
		bp.add("camera");
		bp.add("front light");
		bp.add("rotation thruster 1");
		bp.add("rotation thruster 2");
		bp.add("rear thruster");
		bp.add("frontal left thruster");
		bp.add("frontal right thruster");
		bp.add("side left thruster");
		bp.add("side right thruster");
		bp.add("boost");
		BlueprintLibrary.save(bp);
		
		// asteroid
		bp = new Blueprint("asteroid");
		bp.add(new Naming("asteroid"));
		bp.add(new PlanarStance());
		bp.add(new Dragging(0.7));
		bp.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		bp.add(new Model("rock01.jpg", 1, new Angle(0), new Angle(0), new Angle(0)));
		bp.add(new Physic(Point2D.ORIGIN, new PhysicStat("asteroid", 1000, new CollisionShape(2), new Fraction(0.5)), null));
		bp.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		bp.add(new Attrition(500, 500));
		BlueprintLibrary.save(bp);
	}
}
