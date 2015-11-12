package model;

import com.simsilica.es.EntityData;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.ECS.EntityDataObserver;
import model.ECS.PostingEntityData;
import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.assets.Ability;
import model.ES.component.assets.AbilityTrigger;
import model.ES.component.assets.Attackable;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.Health;
import model.ES.component.assets.Projectile;
import model.ES.component.assets.ProjectileLauncher;
import model.ES.component.assets.RotationThruster;
import model.ES.component.assets.Thruster;
import model.ES.component.assets.TriggerRepeater;
import model.ES.component.audio.AudioSource;
import model.ES.component.audio.ThrusterAudioSource;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlanarNeededRotation;
import model.ES.component.command.PlanarNeededThrust;
import model.ES.component.command.PlayerControl;
import model.ES.component.hierarchy.AbilityControl;
import model.ES.component.hierarchy.AbilityTriggerControl;
import model.ES.component.hierarchy.BoneHolding;
import model.ES.component.hierarchy.PlanarStanceControl;
import model.ES.component.hierarchy.ThrustControl;
import model.ES.component.hierarchy.ThrusterControl;
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
import model.ES.component.motion.RandomVelocityToApply;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.component.motion.physic.PhysicForce;
import model.ES.component.motion.physic.RandomDragging;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCaster;
import model.ES.component.visuals.Skeleton;
import model.ES.component.visuals.Sprite;
import model.ES.component.world.Scenery;
import model.world.WorldData;

public class Model {
	public final Inspector inspector;
	public final Hierarchy hierarchy;
	public final EntityDataObserver observer;
	public final ResourceExplorer resourceExplorer;
	
	private final EntityData ed;
	private final WorldData world;
	
	public final ObjectProperty<EntityPresenter> selectionProperty = new SimpleObjectProperty<>();
	
	public Model() {
		ed = new PostingEntityData();
		world = new WorldData(ed);
		
		// TODO
		// max value
		// destruction of removed lights
		observer = new EntityDataObserver(ed);
		
		inspector = new Inspector(ed, selectionProperty);
		inspector.addUserComponent(Naming.class,
				PlanarStance.class,
				PlayerControl.class,
				model.ES.component.visuals.Model.class,
				Sprite.class,
				ChasingCamera.class,
				Sighting.class,
				Touching.class,
				DamageOnTouch.class,
				Damaging.class,
				DestroyedOnTouch.class,
				EffectOnTouch.class,
				ShockwaveOnTouch.class,
				StickOnCollision.class,
				Collisioning.class,
				Dragging.class,
				RandomDragging.class,
				Physic.class,
				PhysicForce.class,
				MotionCapacity.class,
				PlanarVelocityToApply.class,
				RandomVelocityToApply.class,
				PlanarNeededThrust.class,
				PlanarNeededRotation.class,
				AbilityTrigger.class,
				Attackable.class,
				BoneHolding.class,
				PlanarStanceControl.class,
				Attrition.class,
				Health.class,
				Projectile.class,
				ProjectileLauncher.class,
				RotationThruster.class,
				Thruster.class,
				Ability.class,
				TriggerRepeater.class,
				Lighting.class,
				ParticleCaster.class,
				Skeleton.class,
				Cooldown.class,
				LifeTime.class,
				ToRemove.class,
				ModelRotation.class,
				ThrusterControl.class,
				ThrusterAudioSource.class,
				AudioSource.class,
				AbilityControl.class,
				AbilityTriggerControl.class,
				ThrustControl.class,
				Scenery.class);
		
		hierarchy = new Hierarchy(ed);
		resourceExplorer = new ResourceExplorer();
	}
	
	public EntityData getEntityData() {
		return ed;
	}

	public WorldData getWorld() {
		return world;
	}
	
}
