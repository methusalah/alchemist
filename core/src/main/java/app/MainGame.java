package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.simsilica.es.EntityComponent;
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
import model.ES.component.hierarchy.ThrustControl;
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
import model.ES.component.motion.physic.RandomDragging;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCaster;
import model.ES.component.visuals.Sprite;
import model.ES.richData.ColorData;
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
import model.ES.serial.BlueprintLibrary;
import model.ES.serial.EntityInstance;
import model.world.Region;
import model.world.RegionManager;
import model.world.WorldData;
import model.world.terrain.heightmap.Height;
import model.world.terrain.heightmap.HeightMap;

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

		Region r = new Region(new Point2D(-5, 5));
		for(int i = 0; i < 20; i++){
			Point2D coord = new Point2D(-RandomUtil.next()*64, RandomUtil.next()*64);
			Angle a = new Angle(RandomUtil.next()*AngleUtil.FULL);
			PlanarStance stance = new PlanarStance(coord, a, 0.5, Point3D.UNIT_Z);
			
			List<EntityComponent> comps = new ArrayList<>();
			comps.add(stance);
			EntityInstance ei = new EntityInstance(BlueprintLibrary.getBlueprint("thruster"), comps);
			r.getEntities().add(ei);
		}
		HeightMap hm = r.getTerrain().getHeightMap();
		for(int i = 0; i < hm.getAll().size(); i++)
			hm.set(i, new Height(i, RandomUtil.between(-1.5, 0)));
		
		r.getTerrain().getAtlas().getLayers().get(0).set(5, 5, 0);	
		r.getTerrain().getAtlas().getLayers().get(1).set(5, 5, 255);	
		r.getTerrain().getAtlas().getLayers().get(0).set(6, 6, 0);	
		r.getTerrain().getAtlas().getLayers().get(1).set(6, 6, 255);	
		r.getTerrain().getAtlas().getLayers().get(0).set(7, 5, 5);	
		r.getTerrain().getAtlas().getLayers().get(1).set(7, 5, 250);	
		RegionManager.saveRegion(r);
		
		
		
		
		
		
		serialisePrototypes();
		
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
			ed.setComponent(eid, new CircleCollisionShape(2*scale));
			ed.setComponent(eid, new Physic(Point2D.ORIGIN, "asteroid", new ArrayList<>(), 200*scale, new Fraction(0.5), null));
			ed.setComponent(eid, new Model(ed.getComponent(eid, Model.class).getPath(), scale, new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT)), new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT)), new Angle(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT))));
			
			ed.setComponent(eid, new ModelRotation(RandomUtil.between(50, 200), 0, 0));
		}

		
		stateManager.attach(new TopdownCtrl());
		stateManager.getState(TopdownCtrl.class).setEnabled(true);
		currentAppState = stateManager.getState(TopdownCtrl.class);
		
		
		EntitySystem es = new EntitySystem(ed, new WorldData(ed));
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
				false,
				new Fraction(1));
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
				false,
				new Fraction(1));
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
				false,
				new Fraction(1));
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
				false,
				new Fraction(1));
	}

	private ParticleCaster getSMOKE(){
		return new ParticleCaster("particles/flame.png",
				2,
				2,
				0,
				0,
				false,
				1000,
				50,
				0.05,
				0.25,
				new ColorData(0.8f, 0.1f, 0.1f, 0.1f),
				new ColorData(0.2f, 0.5f, 0.5f, 0.5f),
				1,
				2,
				0,
				false,
				ParticleCaster.Facing.Camera,
				false,
				0.05,
				false,
				new Fraction(1));
	}
	
	private void serialisePrototypes(){
		EntityPrototype proto;
		
		// sun light
		proto = new EntityPrototype("sun");
		proto.add(new Naming("Sun"));
		proto.add(new Lighting(new ColorData(255, 255, 255, 255), 1.5, Double.POSITIVE_INFINITY, 0, 0, true, new Fraction(1)));
		proto.add(new SpaceStance(Point3D.ORIGIN, new Point3D(-1, -1, -3)));
		PrototypeLibrary.save(proto);

		// rotation thruster particle
		proto = new EntityPrototype("rotation thruster particle");
		proto.add(new Naming("rotation thruster particle"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, 0, 0), new Angle(0)));
		proto.add(new PlanarStance());
		proto.add(new ThrusterControl());
		proto.add(getCaster1());
		PrototypeLibrary.save(proto);

		// rotation thrusters
		proto = new EntityPrototype("rotation thruster 1");
		proto.add(new Naming("rotation thruster 1"));
		proto.add(new PlanarStanceControl(true, new Point3D(0.5, 0.2, 0), new Angle(-AngleUtil.toRadians(20))));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new ThrustControl(true));
		proto.add(new RotationThruster(true, AngleUtil.toRadians(5), new Fraction(0), false));
		proto.add("rotation thruster particle");
		PrototypeLibrary.save(proto);

		proto = new EntityPrototype("rotation thruster 2");
		proto.add(new Naming("rotation thruster 2"));
		proto.add(new PlanarStanceControl(true, new Point3D(0.5, -0.2, 0), new Angle(-AngleUtil.toRadians(20))));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new ThrustControl(true));
		proto.add(new RotationThruster(false, AngleUtil.toRadians(-5), new Fraction(0), false));
		proto.add("rotation thruster particle");
		PrototypeLibrary.save(proto);

		// main thruster
		proto = new EntityPrototype("rear thruster");
		proto.add(new Naming("rear thruster"));
		proto.add(new PlanarStanceControl(true, new Point3D(-0.7, 0, 0), new Angle(AngleUtil.FLAT)));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new ThrustControl(true));
		proto.add(new Thruster(new Point3D(1, 0, 0), new Angle(AngleUtil.toRadians(90)), new Fraction(0), false));
		proto.add(new Lighting(new ColorData(255, 255, 150, 150), 3, 6, AngleUtil.toRadians(10), AngleUtil.toRadians(60), false, new Fraction(0)));
		proto.add("rear thruster particle");
		proto.add("rear thruster sound");
		PrototypeLibrary.save(proto);

		// main thruster particle
		proto = new EntityPrototype("rear thruster particle");
		proto.add(new Naming("rear thruster particle"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, 0, 0), new Angle(0)));
		proto.add(new PlanarStance());
		proto.add(new ThrusterControl());
		proto.add(getCaster2());
		PrototypeLibrary.save(proto);

		// main thruster sound
		proto = new EntityPrototype("rear thruster sound");
		proto.add(new Naming("rear thruster sound"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, 0, 0), new Angle(0)));
		proto.add(new PlanarStance());
		proto.add(new ThrusterControl());
		proto.add(new ThrusterAudioSource("thruster_start.wav", "thruster_loop.wav", "thruster_stop.wav", new Fraction(1)));
		PrototypeLibrary.save(proto);
		
		// maneuver thruster particle
		proto = new EntityPrototype("maneuver thruster particle");
		proto.add(new Naming("maneuver thruster particle"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, 0, 0), new Angle(0)));
		proto.add(new PlanarStance());
		proto.add(new ThrusterControl());
		proto.add(getCaster3());
		PrototypeLibrary.save(proto);

		// front thrusters
		proto = new EntityPrototype("frontal left thruster");
		proto.add(new Naming("frontal left thruster"));
		proto.add(new PlanarStanceControl(true, new Point3D(0.4, 0.15, 0), new Angle(AngleUtil.toRadians(20))));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new ThrustControl(true));
		proto.add(new Thruster(new Point3D(-1, -1, 0), new Angle(AngleUtil.toRadians(70)), new Fraction(0), true));
		proto.add("maneuver thruster particle");
		PrototypeLibrary.save(proto);

		proto = new EntityPrototype("frontal right thruster");
		proto.add(new Naming("frontal right thruster"));
		proto.add(new PlanarStanceControl(true, new Point3D(0.4, -0.15, 0), new Angle(AngleUtil.toRadians(-20))));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new ThrustControl(true));
		proto.add(new Thruster(new Point3D(-1, 1, 0), new Angle(AngleUtil.toRadians(70)), new Fraction(0), true));
		proto.add("maneuver thruster particle");
		PrototypeLibrary.save(proto);

		// lateral thrusters
		proto = new EntityPrototype("side left thruster");
		proto.add(new Naming("side left thruster"));
		proto.add(new PlanarStanceControl(true, new Point3D(-0.34, 0.2, 0), new Angle(AngleUtil.toRadians(110))));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new ThrustControl(true));
		proto.add(new Thruster(new Point3D(1, -1.5, 0), new Angle(AngleUtil.toRadians(50)), new Fraction(0), true));
		proto.add("maneuver thruster particle");
		PrototypeLibrary.save(proto);

		proto = new EntityPrototype("side right thruster");
		proto.add(new Naming("side right thruster"));
		proto.add(new PlanarStanceControl(true, new Point3D(-0.34, -0.2, 0), new Angle(AngleUtil.toRadians(-110))));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new ThrustControl(true));
		proto.add(new Thruster(new Point3D(1, 1.5, 0), new Angle(AngleUtil.toRadians(50)), new Fraction(0), true));
		proto.add("maneuver thruster particle");
		PrototypeLibrary.save(proto);
		
		
		
		
		
		
		// explosion debris
		proto = new EntityPrototype("debris");
		proto.add(new Naming("explosion debris"));
		proto.add(new PlanarStance());
		proto.add(new RandomVelocityToApply(10, 30));
		proto.add(new RandomDragging(0.005, 0.03));
		List<String> exceptions = new ArrayList<>();
		exceptions.add("Missile");
		proto.add(new Physic(Point2D.ORIGIN, "debris", exceptions, 1, new Fraction(0.4), null));
		proto.add(new CircleCollisionShape(0.1));
		proto.add(new Model("human/adav/adav02b.mesh.xml", 0.0005, new Angle(0), new Angle(AngleUtil.toRadians(-90)), new Angle(0)));
		proto.add("debris particle");
		PrototypeLibrary.save(proto);

		// debris particle
		proto = new EntityPrototype("debris particle");
		proto.add(new Naming("debris particle"));
		proto.add(new PlanarStance());
		proto.add(new PlanarStanceControl());
		proto.add(getSMOKE());
		proto.add(new LifeTime(0, 1000));
		PrototypeLibrary.save(proto);
		
		// enemy explosion
		proto = new EntityPrototype("enemy explosion");
		proto.add(new Naming("enemy explosion"));
		proto.add(new PlanarStance());
		proto.add(new LifeTime(0, 600));
		proto.add(getEXPLOSION());
		proto.add(new Spawning("debris", 4, 8));
		PrototypeLibrary.save(proto);

		
		
		
		
		
		// enemy
		proto = new EntityPrototype("enemy");
		proto.add(new Naming("enemy"));
		proto.add(new PlanarStance(new Point2D(10, 10), new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new Model("human/adav/adav02b.mesh.xml", 0.0025, new Angle(0), new Angle(AngleUtil.toRadians(-90)), new Angle(0)));
		proto.add(new Physic(Point2D.ORIGIN, "Ship", new ArrayList<>(), 100, new Fraction(0.8), null));
		proto.add(new CircleCollisionShape(0.5));
		proto.add(new Dragging(0.8));
		proto.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		proto.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		proto.add(new Attrition(30, 30, "enemy explosion"));
		proto.add(new Sighting(8, AngleUtil.toRadians(100), new ArrayList<>()));
		proto.add(new AbilityTrigger(new HashMap<>()));
		proto.add("enemy weapon");
		proto.add("rear thruster");
		proto.add("frontal left thruster");
		proto.add("frontal right thruster");
		proto.add("side left thruster");
		proto.add("side right thruster");
		PrototypeLibrary.save(proto);
		
		
		
		// enemy weapon
		proto = new EntityPrototype("enemy weapon");
		proto.add(new Naming("enemy weapon"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, -0.3, 0), new Angle(0)));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new Cooldown(0, 1000));
		proto.add(new TriggerRepeater(300, 60, 10, 0, 0));
		proto.add(new Ability("gun", false));
		proto.add(new AbilityTriggerControl(true));
		proto.add(new ProjectileLauncher(new Fraction(0.97), ""));
		PrototypeLibrary.save(proto);

		
		
		
		
		// player weapons
		proto = new EntityPrototype("weapon left");
		proto.add(new Naming("weapon left"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, 0.3, 0), new Angle(0)));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new Cooldown(0, 100));
		proto.add(new Ability("gun", false));
		proto.add(new AbilityTriggerControl(true));
		proto.add(new ProjectileLauncher(new Fraction(0.995), ""));
		PrototypeLibrary.save(proto);

		proto = new EntityPrototype("weapon right");
		proto.add(new Naming("weapon right"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, -0.3, 0), new Angle(0)));
		proto.add(new PlanarStance(Point2D.ORIGIN, new Angle(0), 0, Point3D.UNIT_Z));
		proto.add(new Cooldown(0, 100));
		proto.add(new Ability("gun", false));
		proto.add(new AbilityTriggerControl(true));
		proto.add(new ProjectileLauncher(new Fraction(0.995), ""));
		proto.add("weapon sound");
		PrototypeLibrary.save(proto);
		
		// weapon sound
		proto = new EntityPrototype("weapon sound");
		proto.add(new Naming("weapon sound"));
		proto.add(new PlanarStanceControl(true, new Point3D(0, 0, 0), new Angle(0)));
		proto.add(new PlanarStance());
		proto.add(new AbilityControl(true));
		proto.add(new AudioSource("monoshot1.wav", false, new Fraction(1)));
		PrototypeLibrary.save(proto);

		// front light
		proto = new EntityPrototype("front light");
		proto.add(new Naming("front light"));
		proto.add(new PlanarStanceControl(true, new Point3D(1, 0, 0), new Angle(0)));
		proto.add(new SpaceStance(Point3D.ORIGIN, Point3D.ORIGIN));
		proto.add(new Lighting(new ColorData(255, 255, 255, 255), 4, 6, AngleUtil.toRadians(30), AngleUtil.toRadians(40), false, new Fraction(1)));
		PrototypeLibrary.save(proto);

		// nebula
		proto = new EntityPrototype("nebula");
		proto.add(new Naming("nebula"));
		proto.add(new PlanarStance());
		proto.add(new Sprite("red_nebula.jpg", 100));
		proto.add(new PlanarStanceControl(true, new Point3D(0, 0, -30), new Angle(0)));
		PrototypeLibrary.save(proto);

		// camera
		proto = new EntityPrototype("camera");
		proto.add(new Naming("camera"));
		proto.add(new PlanarStance(new Point2D(1, 1), new Angle(0), 25, Point3D.UNIT_Z));
		proto.add(new ChasingCamera(Double.MAX_VALUE, 0, 5, 5, Point3D.ORIGIN, Point3D.ORIGIN));
		proto.add(new MotionCapacity(AngleUtil.toRadians(500), 1, 1, 1));
		proto.add("nebula");
		PrototypeLibrary.save(proto);
		
		// boost
		proto = new EntityPrototype("boost");
		proto.add(new Naming("boost"));
		proto.add(new Boost(120));
		proto.add(new Ability("boost", false));
		proto.add(new AbilityTriggerControl());
		PrototypeLibrary.save(proto);
		
		proto = new EntityPrototype("player ship");
		proto.add(new Naming("player ship"));
		proto.add(new PlayerControl());
		proto.add(new PlanarStance(new Point2D(1, 1), new Angle(0), 0.5, Point3D.UNIT_Z));
		proto.add(new Dragging(0.5));
		proto.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		proto.add(new Model("human/adav/adav02b.mesh.xml", 0.0025, new Angle(0), new Angle(-AngleUtil.RIGHT), new Angle(0)));
		proto.add(new Physic(Point2D.ORIGIN, "Ship", new ArrayList<>(), 100, new Fraction(0.8), null));
		proto.add(new CircleCollisionShape(0.5));
		proto.add(new EffectOnTouch());
		proto.add(new VelocityViewing());
		proto.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		proto.add(new PlanarNeededThrust(Point2D.ORIGIN));
		proto.add(new Attackable());
		proto.add(new AbilityTrigger(new HashMap<>()));
		proto.add("weapon left");
		proto.add("weapon right");
		proto.add("camera");
		proto.add("front light");
		proto.add("rotation thruster 1");
		proto.add("rotation thruster 2");
		proto.add("rear thruster");
		proto.add("frontal left thruster");
		proto.add("frontal right thruster");
		proto.add("side left thruster");
		proto.add("side right thruster");
		proto.add("boost");
		PrototypeLibrary.save(proto);
		
		// asteroid
		proto = new EntityPrototype("asteroid");
		proto.add(new Naming("asteroid"));
		proto.add(new PlanarStance());
		proto.add(new Dragging(0.7));
		proto.add(new CircleCollisionShape(2));
		proto.add(new MotionCapacity(AngleUtil.toRadians(720), 30, 10, 10));
		proto.add(new Model("env/exterior01/asteroid/asteroid_export_01.j3o", 1, new Angle(0), new Angle(0), new Angle(0)));
		proto.add(new Physic(Point2D.ORIGIN, "asteroid", new ArrayList<>(), 1000, new Fraction(0.5), null));
		
		proto.add(new PlanarVelocityToApply(Point2D.ORIGIN));
		proto.add(new Attrition(50, 50, "enemy explosion"));
		PrototypeLibrary.save(proto);
	}
}
