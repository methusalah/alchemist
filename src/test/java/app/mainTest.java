package app;

import model.ECS.pipeline.Pipeline;
import presentation.sceneView.RegionPager;
import presentation.sceneView.WorldLocaliserState;
import processor.logic.ChasingCameraProc;
import processor.logic.LifeTimeProc;
import processor.logic.ParentingCleanerProc;
import processor.logic.RemovedCleanerProc;
import processor.logic.RemoverProc;
import processor.logic.AI.BehaviorTreeProc;
import processor.logic.ability.AbilityCoolDownProc;
import processor.logic.ability.AbilityProc;
import processor.logic.ability.AbilityTriggerResetProc;
import processor.logic.ability.BoostProc;
import processor.logic.ability.ProjectileLauncherProc;
import processor.logic.ability.TriggerRepeaterProc;
import processor.logic.combat.resistance.ShieldProc;
import processor.logic.combat.resistance.SpawnOnShieldDepletedProc;
import processor.logic.command.NeededRotationProc;
import processor.logic.command.NeededThrustProc;
import processor.logic.command.PlayerAbilityControlProc;
import processor.logic.command.PlayerRotationControlProc;
import processor.logic.command.PlayerThrustControlProc;
import processor.logic.holder.BoneHoldingProc;
import processor.logic.holder.PlanarHoldingProc;
import processor.logic.interaction.DestroyedOnTouchProc;
import processor.logic.interaction.ShockwaveOnTouchProc;
import processor.logic.interaction.SpawnMultipleOnBornProc;
import processor.logic.interaction.SpawnOnDecayProc;
import processor.logic.interaction.SpawnOnTouchProc;
import processor.logic.interaction.TouchingClearingProc;
import processor.logic.interaction.damage.DamageOnTouchProc;
import processor.logic.interaction.damage.DamagingOverTimeProc;
import processor.logic.interaction.damage.DamagingProc;
import processor.logic.motion.RandomVelocityApplicationProc;
import processor.logic.motion.VelocityApplicationProc;
import processor.logic.motion.physic.DraggingProc;
import processor.logic.motion.physic.PhysicForceProc;
import processor.logic.motion.physic.RandomDraggingProc;
import processor.logic.motion.physic.collisionDetection.CircleCircleCollisionProc;
import processor.logic.motion.physic.collisionDetection.CircleEdgeCollisionProc;
import processor.logic.motion.physic.collisionDetection.CollisionResolutionProc;
import processor.logic.motion.physic.collisionDetection.EdgeEdgeCollisionProc;
import processor.logic.senses.SightProc;
import processor.logic.shipGear.AttritionProc;
import processor.logic.shipGear.LightThrusterProc;
import processor.logic.shipGear.RotationThrusterProc;
import processor.logic.shipGear.ThrusterProc;
import processor.rendering.CameraPlacingProc;
import processor.rendering.LightProc;
import processor.rendering.audio.AbilityAudioProc;
import processor.rendering.audio.AudioSourcePlacingProc;
import processor.rendering.audio.AudioSourceProc;
import processor.rendering.audio.ThrusterAudioProc;
import processor.rendering.model.ModelPlacingProc;
import processor.rendering.model.ModelProc;
import processor.rendering.model.ModelRotationProc;
import processor.rendering.particle.ParticlePlacingProc;
import processor.rendering.particle.ParticleProc;
import processor.rendering.particle.ParticleThrusterProc;
import processor.rendering.sprite.SpritePlacingProc;
import processor.rendering.sprite.SpriteProc;
import processor.rendering.ui.FloatingLabelProc;
import processor.rendering.ui.VelocityVisualisationProc;
import view.Alchemist;

public class mainTest extends Alchemist {
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	@Override
	protected void onIntialize() {
		Pipeline visualPipeline = pipelineProvider.createRendererPipeline();
		visualPipeline.addProcessor(new ModelProc());
		visualPipeline.addProcessor(new SpriteProc());
		visualPipeline.addProcessor(new ParticleProc());
		//visualStates.add(new RagdollProc());
		//visualStates.add(new EdgeCollisionShapeDrawingProc());
		
		visualPipeline.addProcessor(new ModelPlacingProc());
		visualPipeline.addProcessor(new SpritePlacingProc());
		visualPipeline.addProcessor(new ParticlePlacingProc());
		visualPipeline.addProcessor(new ParticleThrusterProc());
		
		visualPipeline.addProcessor(new ModelRotationProc());
		visualPipeline.addProcessor(new LightProc());
		visualPipeline.addProcessor(new VelocityVisualisationProc());
		visualPipeline.addProcessor(new FloatingLabelProc());

		Pipeline audioPipeline = pipelineProvider.createRendererPipeline();
		audioPipeline.addProcessor(new AudioSourceProc());
		audioPipeline.addProcessor(new AudioSourcePlacingProc());
		audioPipeline.addProcessor(new ThrusterAudioProc());
		audioPipeline.addProcessor(new AbilityAudioProc());

		Pipeline commandPipeline = pipelineProvider.createRendererPipeline();
		commandPipeline.addProcessor(new PlayerRotationControlProc());
		commandPipeline.addProcessor(new PlayerThrustControlProc());
		commandPipeline.addProcessor(new CameraPlacingProc());
		
		Pipeline logicPipeline = pipelineProvider.createIndependantPipeline();
		logicPipeline.addProcessor(new ChasingCameraProc());
		logicPipeline.addProcessor(new RotationThrusterProc());
		logicPipeline.addProcessor(new ThrusterProc());
		// forces
		logicPipeline.addProcessor(new NeededRotationProc());
		logicPipeline.addProcessor(new NeededThrustProc());
		logicPipeline.addProcessor(new RandomDraggingProc());
		logicPipeline.addProcessor(new DraggingProc());
		logicPipeline.addProcessor(new PhysicForceProc());
		logicPipeline.addProcessor(new RandomVelocityApplicationProc());
		logicPipeline.addProcessor(new BoostProc());
		logicPipeline.addProcessor(new VelocityApplicationProc());
		// collisions
		logicPipeline.addProcessor(new TouchingClearingProc());
		logicPipeline.addProcessor(new CircleCircleCollisionProc());
		logicPipeline.addProcessor(new CircleEdgeCollisionProc());
		logicPipeline.addProcessor(new EdgeEdgeCollisionProc());
		logicPipeline.addProcessor(new CollisionResolutionProc());
		// relations	
		logicPipeline.addProcessor(new BoneHoldingProc());
		logicPipeline.addProcessor(new PlanarHoldingProc());
		logicPipeline.addProcessor(new LightThrusterProc());
		
		// ability
		logicPipeline.addProcessor(new PlayerAbilityControlProc());
		logicPipeline.addProcessor(new BehaviorTreeProc());
		logicPipeline.addProcessor(new AbilityProc());
		logicPipeline.addProcessor(new AbilityTriggerResetProc());
		logicPipeline.addProcessor(new AbilityCoolDownProc());
		logicPipeline.addProcessor(new TriggerRepeaterProc());
		
		
		logicPipeline.addProcessor(new SpawnMultipleOnBornProc());
		logicPipeline.addProcessor(new ProjectileLauncherProc());

		logicPipeline.addProcessor(new DamagingProc());
		logicPipeline.addProcessor(new DamagingOverTimeProc());
		logicPipeline.addProcessor(new AttritionProc());
		logicPipeline.addProcessor(new ShieldProc());
		logicPipeline.addProcessor(new SpawnOnShieldDepletedProc());

		logicPipeline.addProcessor(new SightProc());
		
		logicPipeline.addProcessor(new SpawnOnTouchProc());
		logicPipeline.addProcessor(new DamageOnTouchProc());
		logicPipeline.addProcessor(new DestroyedOnTouchProc());
		logicPipeline.addProcessor(new ShockwaveOnTouchProc());
		
		logicPipeline.addProcessor(new LifeTimeProc());
		logicPipeline.addProcessor(new SpawnOnDecayProc());
		
		logicPipeline.addProcessor(new RemovedCleanerProc());
		logicPipeline.addProcessor(new RemoverProc());
		logicPipeline.addProcessor(new ParentingCleanerProc());
		
		
		stateManager.attach(new WorldLocaliserState());
		stateManager.attach(new RegionPager());
		stateManager.getState(RegionPager.class).setEnabled(true);

	}
}
