package application;

import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.ToRemove;
import model.ES.component.assets.Ability;
import model.ES.component.assets.AbilityTrigger;
import model.ES.component.assets.Attackable;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.Boost;
import model.ES.component.assets.Projectile;
import model.ES.component.assets.ProjectileLauncher;
import model.ES.component.assets.RotationThruster;
import model.ES.component.assets.Thruster;
import model.ES.component.assets.TriggerRepeater;
import model.ES.component.assets.damage.DamageCapacity;
import model.ES.component.assets.damage.DamageOverTime;
import model.ES.component.audio.AudioSource;
import model.ES.component.audio.ThrusterAudioSource;
import model.ES.component.behavior.RagdollOnDestroy;
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
import model.ES.component.motion.SpaceStance;
import model.ES.component.motion.physic.CircleCollisionShape;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.EdgedCollisionShape;
import model.ES.component.motion.physic.Physic;
import model.ES.component.motion.physic.PhysicForce;
import model.ES.component.motion.physic.RandomDragging;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.Model;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCaster;
import model.ES.component.visuals.Skeleton;
import model.ES.component.visuals.Sprite;
import model.ES.component.world.PopulationTooling;
import model.ES.component.world.TerrainTooling;
import presenter.util.UserComponentList;

public class MyUserComponentList extends UserComponentList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyUserComponentList() {
		add(PlanarStance.class,
				SpaceStance.class,
				PlayerControl.class,
				Model.class,
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
				PopulationTooling.class,
				TerrainTooling.class,
				Boost.class,
				CircleCollisionShape.class,
				EdgedCollisionShape.class,
				RagdollOnDestroy.class,
				DamageOverTime.class,
				DamageCapacity.class);
		}
}
