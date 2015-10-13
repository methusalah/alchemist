package model;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.base.DefaultEntityData;

import app.AppFacade;
import controller.cameraManagement.ChasingCameraProc;
import controller.entityAppState.EntityDataAppState;
import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.camera.ChasingCamera;
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
import model.ES.component.visuals.ParticleCasting;
import model.ES.component.visuals.Skeleton;
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
import model.ES.serial.BlueprintCreator;
import view.drawingProcessors.LightProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.PlacingModelProc;
import view.drawingProcessors.VelocityVisualisationProc;

public class Model {
	public final Inspector inspector;
	public final Hierarchy hierarchy;
	public final JmeForImageView jme;
	
	public Model() {
		DefaultEntityData ed = new DefaultEntityData();
		BlueprintCreator.setEntityData(ed);
		BlueprintCreator.create("player ship", null);
		BlueprintCreator.create("enemy", null);
		BlueprintCreator.create("sun", null);

		inspector = new Inspector(ed);
		inspector.addComponentToScan(Naming.class);
		inspector.addComponentToScan(PlanarStance.class);
		inspector.addComponentToScan(PlayerControl.class);
		inspector.addComponentToScan(model.ES.component.visuals.Model.class);
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
		

		hierarchy = new Hierarchy(ed);
		
		jme = new JmeForImageView();
		jme.enqueue((app) -> createScene(app, ed));
//		jme.enqueue((app) -> setFPS(app));
	}
	
	static boolean setFPS(SimpleApplication app){
		AppSettings settings = new AppSettings(false);
		settings.setFullscreen(false);
		settings.setUseInput(false);
		settings.setFrameRate(0);
		settings.setCustomRenderer(com.jme3x.jfx.injfx.JmeContextOffscreenSurface.class);
		app.setSettings(settings);
		app.restart();
		return true;
	}
	static boolean createScene(SimpleApplication app, DefaultEntityData ed) {
		AppStateManager stateManager = app.getStateManager();
		
//		stateManager.attach(new TopdownCtrl());
//		stateManager.getState(TopdownCtrl.class).setEnabled(true);
		AppFacade.setApp(app);
		
		stateManager.attach(new EntityDataAppState(ed));
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
		stateManager.attach(new ChasingCameraProc(app.getCamera()));
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
		stateManager.attach(new TriggerRepeaterProc());
		
		stateManager.attach(new ProjectileLauncherProc());

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
		return true;
	}
}
