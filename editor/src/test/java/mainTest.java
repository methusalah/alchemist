package test.java;

import main.java.model.ECS.pipeline.Pipeline;
import main.java.view.Alchemist;
import test.java.processor.logic.LifeTimeProc;
import test.java.processor.logic.ParentingCleanerProc;
import test.java.processor.logic.RemovedCleanerProc;
import test.java.processor.logic.RemoverProc;
import test.java.processor.logic.AI.BehaviorTreeProc;
import test.java.processor.logic.ability.AbilityCoolDownProc;
import test.java.processor.logic.ability.AbilityProc;
import test.java.processor.logic.ability.AbilityTriggerResetProc;
import test.java.processor.logic.ability.BoostProc;
import test.java.processor.logic.ability.ProjectileLauncherProc;
import test.java.processor.logic.ability.TriggerRepeaterProc;
import test.java.processor.logic.combat.resistance.ShieldProc;
import test.java.processor.logic.combat.resistance.SpawnOnShieldDepletedProc;
import test.java.processor.logic.command.NeededRotationProc;
import test.java.processor.logic.command.NeededThrustProc;
import test.java.processor.logic.command.PlayerAbilityControlProc;
import test.java.processor.logic.command.PlayerRotationControlProc;
import test.java.processor.logic.command.PlayerThrustControlProc;
import test.java.processor.logic.holder.BoneHoldingProc;
import test.java.processor.logic.holder.PlanarHoldingProc;
import test.java.processor.logic.interaction.DestroyedOnTouchProc;
import test.java.processor.logic.interaction.ShockwaveOnTouchProc;
import test.java.processor.logic.interaction.SpawnMultipleOnBornProc;
import test.java.processor.logic.interaction.SpawnOnDecayProc;
import test.java.processor.logic.interaction.SpawnOnTouchProc;
import test.java.processor.logic.interaction.TouchingClearingProc;
import test.java.processor.logic.interaction.damage.DamageOnTouchProc;
import test.java.processor.logic.interaction.damage.DamagingOverTimeProc;
import test.java.processor.logic.interaction.damage.DamagingProc;
import test.java.processor.logic.motion.RandomVelocityApplicationProc;
import test.java.processor.logic.motion.VelocityApplicationProc;
import test.java.processor.logic.motion.physic.DraggingProc;
import test.java.processor.logic.motion.physic.PhysicForceProc;
import test.java.processor.logic.motion.physic.RandomDraggingProc;
import test.java.processor.logic.motion.physic.collisionDetection.CircleCircleCollisionProc;
import test.java.processor.logic.motion.physic.collisionDetection.CircleEdgeCollisionProc;
import test.java.processor.logic.motion.physic.collisionDetection.CollisionResolutionProc;
import test.java.processor.logic.motion.physic.collisionDetection.EdgeEdgeCollisionProc;
import test.java.processor.logic.senses.SightProc;
import test.java.processor.logic.shipGear.AttritionProc;
import test.java.processor.logic.shipGear.LightThrusterProc;
import test.java.processor.logic.shipGear.RotationThrusterProc;
import test.java.processor.logic.shipGear.ThrusterProc;
import test.java.processor.rendering.CameraPlacingProc;
import test.java.processor.rendering.ChasingCameraProc;
import test.java.processor.rendering.LightProc;
import test.java.processor.rendering.audio.AbilityAudioProc;
import test.java.processor.rendering.audio.AudioSourcePlacingProc;
import test.java.processor.rendering.audio.AudioSourceProc;
import test.java.processor.rendering.audio.ThrusterAudioProc;
import test.java.processor.rendering.model.ModelPlacingProc;
import test.java.processor.rendering.model.ModelProc;
import test.java.processor.rendering.model.ModelRotationProc;
import test.java.processor.rendering.particle.ParticlePlacingProc;
import test.java.processor.rendering.particle.ParticleProc;
import test.java.processor.rendering.particle.ParticleThrusterProc;
import test.java.processor.rendering.sprite.SpritePlacingProc;
import test.java.processor.rendering.sprite.SpriteProc;
import test.java.processor.rendering.ui.FloatingLabelProc;
import test.java.processor.rendering.ui.VelocityVisualisationProc;

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
		
	}
}
