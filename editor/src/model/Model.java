package model;

import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.command.PlayerControl;
import model.ES.component.interaction.DamageOnTouch;
import model.ES.component.interaction.Damaging;
import model.ES.component.interaction.DestroyedOnTouch;
import model.ES.component.interaction.EffectOnTouch;
import model.ES.component.interaction.ShockwaveOnTouch;
import model.ES.component.interaction.StickOnCollision;
import model.ES.component.interaction.senses.Sighting;
import model.ES.component.interaction.senses.Touching;
import model.ES.component.motion.MotionCapacity;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.PlanarVelocityToApply;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.component.motion.physic.PhysicForce;
import model.ES.component.relation.AbilityTriggerList;
import model.ES.component.relation.Attackable;
import model.ES.component.relation.BoneHolding;
import model.ES.component.relation.Parenting;
import model.ES.component.relation.PlanarHolding;
import model.ES.component.shipGear.Attrition;
import model.ES.component.shipGear.Health;
import model.ES.component.shipGear.Projectile;
import model.ES.component.shipGear.ProjectileLauncher;
import model.ES.component.shipGear.RotationThruster;
import model.ES.component.shipGear.Thruster;
import model.ES.component.shipGear.Trigger;
import model.ES.component.shipGear.TriggerRepeater;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCasting;
import model.ES.component.visuals.Skeleton;
import model.ES.component.visuals.Sprite;
import model.ES.serial.BlueprintCreator;
import app.AppFacade;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

import controller.ECS.EntityDataAppState;
import controller.ECS.EntitySystem;

public class Model {
	public final Inspector inspector;
	public final Hierarchy hierarchy;
	public final JmeForImageView jme;
	
	public Model() {
		EntityData ed = new DefaultEntityData();
		BlueprintCreator.setEntityData(ed);
		BlueprintCreator.create("player ship", null);
		BlueprintCreator.create("enemy", null);
		BlueprintCreator.create("sun", null);

		
		inspector = new Inspector(ed);
		inspector.addComponentToScan(Naming.class);
		inspector.addComponentToScan(PlanarStance.class);
		inspector.addComponentToScan(PlayerControl.class);
		inspector.addComponentToScan(model.ES.component.visuals.Model.class);
		inspector.addComponentToScan(Sprite.class);
		inspector.addComponentToScan(ChasingCamera.class);
		inspector.addComponentToScan(Sighting.class);
		inspector.addComponentToScan(Touching.class);
		inspector.addComponentToScan(DamageOnTouch.class);
		inspector.addComponentToScan(Damaging.class);
		inspector.addComponentToScan(DestroyedOnTouch.class);
		inspector.addComponentToScan(EffectOnTouch.class);
		inspector.addComponentToScan(ShockwaveOnTouch.class);
		inspector.addComponentToScan(StickOnCollision.class);
		inspector.addComponentToScan(Collisioning.class);
		inspector.addComponentToScan(Dragging.class);
		inspector.addComponentToScan(Physic.class);
		inspector.addComponentToScan(PhysicForce.class);
		inspector.addComponentToScan(MotionCapacity.class);
		inspector.addComponentToScan(PlanarVelocityToApply.class);
		inspector.addComponentToScan(PlanarNeededThrust.class);
		inspector.addComponentToScan(PlanarNeededRotation.class);
		inspector.addComponentToScan(AbilityTriggerList.class);
		inspector.addComponentToScan(Attackable.class);
		inspector.addComponentToScan(BoneHolding.class);
		inspector.addComponentToScan(Parenting.class);
		inspector.addComponentToScan(PlanarHolding.class);
		inspector.addComponentToScan(Attrition.class);
		inspector.addComponentToScan(Health.class);
		inspector.addComponentToScan(Projectile.class);
		inspector.addComponentToScan(ProjectileLauncher.class);
		inspector.addComponentToScan(RotationThruster.class);
		inspector.addComponentToScan(Thruster.class);
		inspector.addComponentToScan(Trigger.class);
		inspector.addComponentToScan(TriggerRepeater.class);
		inspector.addComponentToScan(Lighting.class);
		inspector.addComponentToScan(ParticleCasting.class);
		inspector.addComponentToScan(Skeleton.class);
		inspector.addComponentToScan(Cooldown.class);
		inspector.addComponentToScan(LifeTime.class);
		inspector.addComponentToScan(ToRemove.class);
		inspector.addComponentToScan(ModelRotation.class);
		
		hierarchy = new Hierarchy(ed);
		
		jme = new JmeForImageView();
		jme.enqueue((app) -> createScene(app, ed));
	}

	static boolean createScene(SimpleApplication app, EntityData ed) {
		AppFacade.setApp(app);
		AppStateManager stateManager = app.getStateManager();
		
		stateManager.attach(new EntityDataAppState(ed));
		stateManager.attach(new EntitySystem(ed));
		return true;
	}
}
