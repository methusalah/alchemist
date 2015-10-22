package model;

import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.assets.AbilityTrigger;
import model.ES.component.assets.Ability;
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
import model.ES.component.hierarchy.BoneHolding;
import model.ES.component.hierarchy.Parenting;
import model.ES.component.hierarchy.PlanarStanceControl;
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
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.Physic;
import model.ES.component.motion.physic.PhysicForce;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCasting;
import model.ES.component.visuals.Skeleton;
import model.ES.component.visuals.Sprite;
import model.ES.serial.PrototypeCreator;
import util.LogUtil;
import app.AppFacade;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;
import com.simsilica.es.StringIndex;
import com.simsilica.es.base.DefaultEntityData;

import controller.ECS.EntityDataAppState;
import controller.ECS.EntitySystem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Model {
	public final Inspector inspector;
	public final Hierarchy hierarchy;
	public final ResourceExplorer resourceExplorer;
	public final JmeForImageView jme;
	
	public final ObjectProperty<EntityPresenter> selectionProperty = new SimpleObjectProperty<>();
	
	public Model() {
		// TODO
		// max value
		// destruction of removed lights
		
		
		EntityData ed = new DefaultEntityData();
//		PrototypeCreator.setEntityData(ed);
//		PrototypeCreator.create("player ship", null);
//		PrototypeCreator.create("enemy", null);
//		PrototypeCreator.create("sun", null);
		
		inspector = new Inspector(ed, selectionProperty);
		
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
		inspector.addComponentToScan(AbilityTrigger.class);
		inspector.addComponentToScan(Attackable.class);
		inspector.addComponentToScan(BoneHolding.class);
		inspector.addComponentToScan(Parenting.class);
		inspector.addComponentToScan(PlanarStanceControl.class);
		inspector.addComponentToScan(Attrition.class);
		inspector.addComponentToScan(Health.class);
		inspector.addComponentToScan(Projectile.class);
		inspector.addComponentToScan(ProjectileLauncher.class);
		inspector.addComponentToScan(RotationThruster.class);
		inspector.addComponentToScan(Thruster.class);
		inspector.addComponentToScan(Ability.class);
		inspector.addComponentToScan(TriggerRepeater.class);
		inspector.addComponentToScan(Lighting.class);
		inspector.addComponentToScan(ParticleCasting.class);
		inspector.addComponentToScan(Skeleton.class);
		inspector.addComponentToScan(Cooldown.class);
		inspector.addComponentToScan(LifeTime.class);
		inspector.addComponentToScan(ToRemove.class);
		inspector.addComponentToScan(ModelRotation.class);
		inspector.addComponentToScan(ThrusterControl.class);
		inspector.addComponentToScan(ThrusterAudioSource.class);
		inspector.addComponentToScan(AudioSource.class);
		
		
		hierarchy = new Hierarchy(ed);
		resourceExplorer = new ResourceExplorer();
		
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
