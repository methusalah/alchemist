package model;

import model.ES.component.Naming;
import model.ES.component.camera.ChasingCamera;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
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
import app.AppFacade;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

import controller.cameraManagement.ChasingCameraProc;
import controller.entityAppState.EntityDataAppState;

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

		hierarchy = new Hierarchy(ed);
		
		jme = new JmeForImageView();
		jme.enqueue((app) -> createScene(app, ed));
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
