package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;

import controller.Controller;
import controller.ECS.EntitySystem;
import controller.topdown.TopdownCtrl;
import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.assets.Ability;
import model.ES.component.assets.AbilityTrigger;
import model.ES.component.assets.Attackable;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.Boost;
import model.ES.component.assets.ProjectileLauncher;
import model.ES.component.assets.RotationThruster;
import model.ES.component.assets.Spawning;
import model.ES.component.assets.Thruster;
import model.ES.component.assets.TriggerRepeater;
import model.ES.component.audio.AudioSource;
import model.ES.component.audio.ThrusterAudioSource;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.command.PlayerControl;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.hierarchy.AbilityControl;
import model.ES.component.hierarchy.AbilityTriggerControl;
import model.ES.component.hierarchy.PlanarStanceControl;
import model.ES.component.hierarchy.ThrusterControl;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.senses.Sighting;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.RandomVelocityToApply;
import model.ES.component.motion.SpaceStance;
import model.ES.component.motion.physic.CircleCollisionShape;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCasting;
import model.ES.component.visuals.Sprite;
import model.ES.richData.ColorData;
import model.ES.richData.ParticleCaster;
import model.ES.serial.EntityPrototype;
import model.ES.serial.PrototypeCreator;
import model.ES.serial.PrototypeLibrary;
import util.LogUtil;
import util.event.AppStateChangeEvent;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;
import util.math.Fraction;
import util.math.RandomUtil;
import view.material.MaterialManager;

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
		PrototypeCreator.setEntityData(ed);
		
		serialiseBluePrints();
		PrototypeCreator.create("player ship", null);
		int zoneWidth = 80;
		ed.setComponent(PrototypeCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(PrototypeCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(PrototypeCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		ed.setComponent(PrototypeCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
//		ed.setComponent(BlueprintCreator.create("enemy", null), new PlanarStance(new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth), new Angle(RandomUtil.next()*AngleUtil.FULL), 0.5, Point3D.UNIT_Z));
		PrototypeCreator.create("sun", null);
		
		for(int i = 0; i < 50; i++){
			EntityId eid = PrototypeCreator.create("asteroid", null);
			Point2D coord = new Point2D(RandomUtil.next()*zoneWidth, RandomUtil.next()*zoneWidth);
			Angle a = new Angle(RandomUtil.next()*AngleUtil.FULL);
			ed.setComponent(eid, new PlanarStance(coord, a, 0.5, Point3D.UNIT_Z));
			
			double scale = RandomUtil.between(0.5, 3);
			ed.setComponent(eid, new Physic(Point2D.ORIGIN, "asteroid", new ArrayList<>(), 200*scale, new Fraction(0.5), null));
			ed.setComponent(eid, new Model("rock0"+(RandomUtil.nextInt(5)+1)+".obj", scale, new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT)), new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT)), new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT))));
			
			ed.setComponent(eid, new ModelRotation(RandomUtil.between(50, 200), 0, 0));
		}

		
		stateManager.attach(new TopdownCtrl());
		stateManager.getState(TopdownCtrl.class).setEnabled(true);
		currentAppState = stateManager.getState(TopdownCtrl.class);
		
		
		EntitySystem es = new EntitySystem(ed);
		stateManager.attach(es);
		es.initVisuals(true);
		es.initAudio(true);
		es.initCommand(true);
		es.initLogic(true);
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
				new ColorData(255, 255, 255, 255),
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
				new ColorData(1f, 1f, 1f, 1f),
				new ColorData(0, 1f, 0.5f, 0),
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
				new ColorData(1f, 1f, 1f, 1f),
				new ColorData(0, 1f, 0.5f, 0),
				0.05,
				0.1,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0,
				false);
	}

	private ParticleCaster getEXPLOSION(){
		return new ParticleCaster("particles/explosion01.png",
				7,
				7,
				0,
				0,
				false,
				200,
				400,
				0.2,
				0.2,
				new ColorData(0.8f, 1f, 1f, 1f),
				new ColorData(0.4, 1f, 1f, 1),
				0.8,
				0.8,
				0,
				false,
				ParticleCaster.Facing.Camera,
				true,
				0.6,
				false);
	}

	private ParticleCaster getSMOKE(){
		return new ParticleCaster("particles/flame.png",
				2,
				2,
				0,
				0,
				false,
				1000,
				30,
				0.1,
				0.2,
				new ColorData(0.8f, 0.1f, 0.1f, 0.1f),
				new ColorData(0.2f, 0.5f, 0.5f, 0.5f),
				1.5,
				2,
				0,
				false,
				ParticleCaster.Facing.Camera,
				false,
				0.05,
				false);
	}
	
	private void serialiseBluePrints(){
		EntityPrototype bp;
		
		// sun light
		bp = new EntityPrototype("sun");
		bp.add(new Naming("Sun"));
		bp.add(new Lighting(new ColorData(255, 255, 255, 255), 1.5, Double.POSITIVE_INFINITY, 0, 0, true, new Fraction(1)));
		bp.add(new SpaceStance(Point3D.ORIGIN, new Point3D(-1, -1, -3)));
		PrototypeLibrary.save(bp);

		// rotation thrusters
		bp = new EntityPrototype("rotation thruster 1");
		bp.add(new Naming("rotation thruster 1"));
		bp.add(new PlanarStanceControl(new Point3D(0.5, 0.2, 0), new Angle(-AngleUtil.toRadians(20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new RotationThruster(true, AngleUtil.toRadians(5), new Fraction(0), false));
		bp.add(new ParticleCasting(getCaster1(), 0));
		PrototypeLibrary.save(bp);

		bp = new EntityPrototype("rotation thruster 2");
		bp.add(new Naming("rotation thruster 2"));
		bp.add(new PlanarStanceControl(new Point3D(0.5, -0.2, 0), new Angle(-AngleUtil.toRadians(20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new RotationThruster(false, AngleUtil.toRadians(-5), new Fraction(0), false));
		bp.add(new ParticleCasting(getCaster1(), 0));
		PrototypeLibrary.save(bp);

		// main thruster
		bp = new EntityPrototype("rear thruster");
		bp.add(new Naming("rear thruster"));
		bp.add(new PlanarStanceControl(new Point3D(-0.7, 0, 0), new Angle(AngleUtil.FLAT)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(1, 0, 0), new Angle(AngleUtil.toRadians(90)), new Fraction(0), false));
		bp.add(new ParticleCasting(getCaster2(), getCaster2().perSecond));
		bp.add(new Lighting(new ColorData(255, 255, 150, 150), 3, 6, AngleUtil.toRadians(10), AngleUtil.toRadians(60), false, new Fraction(0)));
		bp.add("rear thruster sound");
		PrototypeLibrary.save(bp);

		// main thruster sound
		bp = new EntityPrototype("rear thruster sound");
		bp.add(new Naming("rear thruster sound"));
		bp.add(new PlanarStanceControl(new Point3D(0, 0, 0), new Angle(0)));
		bp.add(new PlanarStance());
		bp.add(new ThrusterControl());
		bp.add(new ThrusterAudioSource("thruster_start.wav", "thruster_loop.wav", "thruster_stop.wav", new Fraction(1)));
		PrototypeLibrary.save(bp);
		

		// front thrusters
		bp = new EntityPrototype("frontal left thruster");
		bp.add(new Naming("frontal left thruster"));
		bp.add(new PlanarStanceControl(new Point3D(0.4, 0.15, 0), new Angle(AngleUtil.toRadians(20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(-1, -1, 0), new Angle(AngleUtil.toRadians(70)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		PrototypeLibrary.save(bp);

		bp = new EntityPrototype("frontal right thruster");
		bp.add(new Naming("frontal right thruster"));
		bp.add(new PlanarStanceControl(new Point3D(0.4, -0.15, 0), new Angle(AngleUtil.toRadians(-20))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(-1, 1, 0), new Angle(AngleUtil.toRadians(70)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		PrototypeLibrary.save(bp);

		// lateral thrusters
		bp = new EntityPrototype("side left thruster");
		bp.add(new Naming("side left thruster"));
		bp.add(new PlanarStanceControl(new Point3D(-0.34, 0.2, 0), new Angle(AngleUtil.toRadians(110))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(1, -1.5, 0), new Angle(AngleUtil.toRadians(50)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		PrototypeLibrary.save(bp);

		bp = new EntityPrototype("side right thruster");
		bp.add(new Naming("side right thruster"));
		bp.add(new PlanarStanceControl(new Point3D(-0.34, -0.2, 0), new Angle(AngleUtil.toRadians(-110))));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Thruster(new Point3D(1, 1.5, 0), new Angle(AngleUtil.toRadians(50)), new Fraction(0), true));
		bp.add(new ParticleCasting(getCaster3(), getCaster3().perSecond));
		PrototypeLibrary.save(bp);
		
		// explosion debris
		bp = new EntityPrototype("debris");
		bp.add(new Naming("explosion debris"));
		bp.add(new PlanarStance());
		bp.add(new RandomVelocityToApply(1, 2));
		List<String> exceptions = new ArrayList<>();
		exceptions.add("Missile");
		bp.add(new Physic(Point2D.UNIT_X, "debris", exceptions, 1, new Fraction(0.4), null));
		bp.add(new CircleCollisionShape(0.1));
		bp.add(new Model("human/adav/adav02b.mesh.xml", 0.0005, new Angle(0), new Angle(AngleUtil.toRadians(-90)), new Angle(0)));
//		bp.add("debris particle");
		PrototypeLibrary.save(bp);

		// debris particle
		bp = new EntityPrototype("debris particle");
		bp.add(new Naming("debris particle"));
		bp.add(new PlanarStance());
		bp.add(new PlanarStanceControl());
		bp.add(new ParticleCasting(getSMOKE(), getSMOKE().perSecond));
		bp.add(new LifeTime(0, 2000));
		PrototypeLibrary.save(bp);
		
		
		
		
		
		
		// enemy explosion
		bp = new EntityPrototype("enemy explosion");
		bp.add(new Naming("enemy explosion"));
		bp.add(new PlanarStance());
		bp.add(new LifeTime(0, 600));
		bp.add(new ParticleCasting(getEXPLOSION(), getEXPLOSION().perSecond));
		bp.add(new Spawning("debris", 3, 7));
		PrototypeLibrary.save(bp);
		
		// enemy
		bp = new EntityPrototype("enemy");
		bp.add(new Naming("enemy"));
		bp.add(new PlanarStance(new Point2D(10, 10), new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Model("human/adav/adav02b.mesh.xml", 0.0025, new Angle(0), new Angle(AngleUtil.toRadians(-90)), new Angle(0)));
		bp.add(new Physic(Point2D.ORIGIN, "Ship", new ArrayList<>(), 100, new Fraction(0.8), null));
		bp.add(new CircleCollisionShape(0.5));
		bp.add(new Dragging(0.5));
		bp.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		bp.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		bp.add(new Attrition(30, 30, "enemy explosion"));
		bp.add(new Sighting(8, AngleUtil.toRadians(100), new ArrayList<>()));
		bp.add(new AbilityTrigger(new HashMap<>()));
		bp.add("enemy weapon");
		bp.add("rear thruster");
		bp.add("frontal left thruster");
		bp.add("frontal right thruster");
		bp.add("side left thruster");
		bp.add("side right thruster");
		PrototypeLibrary.save(bp);
		
		// enemy weapon
		bp = new EntityPrototype("enemy weapon");
		bp.add(new Naming("enemy weapon"));
		bp.add(new PlanarStanceControl(new Point3D(0, -0.3, 0), new Angle(0)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Cooldown(0, 1000));
		bp.add(new TriggerRepeater(300, 60, 10, 0, 0));
		bp.add(new Ability("gun", false));
		bp.add(new AbilityTriggerControl());
		bp.add(new ProjectileLauncher(new Fraction(0.97)));
		PrototypeLibrary.save(bp);

		// player weapons
		bp = new EntityPrototype("weapon left");
		bp.add(new Naming("weapon left"));
		bp.add(new PlanarStanceControl(new Point3D(0, 0.3, 0), new Angle(0)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Cooldown(0, 100));
		bp.add(new Ability("gun", false));
		bp.add(new AbilityTriggerControl());
		bp.add(new ProjectileLauncher(new Fraction(0.995)));
		PrototypeLibrary.save(bp);

		bp = new EntityPrototype("weapon right");
		bp.add(new Naming("weapon right"));
		bp.add(new PlanarStanceControl(new Point3D(0, -0.3, 0), new Angle(0)));
		bp.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		bp.add(new Cooldown(0, 100));
		bp.add(new Ability("gun", false));
		bp.add(new AbilityTriggerControl());
		bp.add(new ProjectileLauncher(new Fraction(0.995)));
		bp.add("weapon sound");
		PrototypeLibrary.save(bp);
		
		// weapon sound
		bp = new EntityPrototype("weapon sound");
		bp.add(new Naming("weapon sound"));
		bp.add(new PlanarStanceControl(new Point3D(0, 0, 0), new Angle(0)));
		bp.add(new PlanarStance());
		bp.add(new AbilityControl());
		bp.add(new AudioSource("monoshot1.wav", false, new Fraction(1)));
		PrototypeLibrary.save(bp);

		// front light
		bp = new EntityPrototype("front light");
		bp.add(new Naming("front light"));
		bp.add(new PlanarStanceControl(new Point3D(1, 0, 0), new Angle(0)));
		bp.add(new SpaceStance(Point3D.ORIGIN, Point3D.ORIGIN));
		bp.add(new Lighting(new ColorData(255, 255, 255, 255), 4, 6, AngleUtil.toRadians(30), AngleUtil.toRadians(40), false, new Fraction(1)));
		PrototypeLibrary.save(bp);

		// nebula
		bp = new EntityPrototype("nebula");
		bp.add(new Naming("nebula"));
		bp.add(new PlanarStance());
		bp.add(new Sprite("red_nebula.jpg", 100));
		bp.add(new PlanarStanceControl(new Point3D(0, 0, -30), new Angle(0)));
		PrototypeLibrary.save(bp);

		// camera
		bp = new EntityPrototype("camera");
		bp.add(new Naming("camera"));
		bp.add(new PlanarStance(new Point2D(1, 1), new Angle(0), 25, Point3D.UNIT_Z));
		bp.add(new ChasingCamera(Double.MAX_VALUE, 0, 5, 5, Point3D.ORIGIN, Point3D.ORIGIN));
		bp.add(new MotionCapacity(AngleUtil.toRadians(500), 1, 1, 1));
		bp.add("nebula");
		PrototypeLibrary.save(bp);
		
		// boost
		bp = new EntityPrototype("boost");
		bp.add(new Naming("boost"));
		bp.add(new Boost(120));
		bp.add(new Ability("boost", false));
		PrototypeLibrary.save(bp);
		
		bp = new EntityPrototype("player ship");
		bp.add(new Naming("player ship"));
		bp.add(new PlayerControl());
		bp.add(new PlanarStance(new Point2D(1, 1), new Angle(0), 0.5, Point3D.UNIT_Z));
		bp.add(new Dragging(0.4));
		bp.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		bp.add(new Model("human/adav/adav02b.mesh.xml", 0.0025, new Angle(0), new Angle(-AngleUtil.RIGHT), new Angle(0)));
		bp.add(new Physic(Point2D.ORIGIN, "Ship", new ArrayList<>(), 100, new Fraction(0.8), null));
		bp.add(new CircleCollisionShape(0.5));
		bp.add(new EffectOnTouch());
		bp.add(new VelocityViewing());
		bp.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		bp.add(new PlanarNeededThrust(Point2D.ORIGIN));
		bp.add(new Attackable());
		bp.add(new AbilityTrigger(new HashMap<>()));
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
		PrototypeLibrary.save(bp);
		
		// asteroid
		bp = new EntityPrototype("asteroid");
		bp.add(new Naming("asteroid"));
		bp.add(new PlanarStance());
		bp.add(new Dragging(0.7));
		bp.add(new CircleCollisionShape(2));
		bp.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		bp.add(new Model("rock01.jpg", 1, new Angle(0), new Angle(0), new Angle(0)));
		bp.add(new Physic(Point2D.ORIGIN, "asteroid", new ArrayList<>(), 1000, new Fraction(0.5), null));
		
		bp.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		bp.add(new Attrition(50, 50, "enemy explosion"));
		PrototypeLibrary.save(bp);
	}
}
