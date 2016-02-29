package app;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.brainless.alchemist.model.state.DataState;
import com.brainless.alchemist.model.state.SceneSelectorState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.presentation.sceneView.SceneViewBehavior;
import com.brainless.alchemist.view.Alchemist;
import com.brainless.alchemist.view.ViewPlatform;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import command.GameInputListener;
import component.motion.PlanarStance;
import logic.processor.logic.ChasingCameraProc;
import logic.processor.logic.LifeTimeProc;
import logic.processor.logic.ParentingCleanerProc;
import logic.processor.logic.RemovedCleanerProc;
import logic.processor.logic.RemoverProc;
import logic.processor.logic.AI.BehaviorTreeProc;
import logic.processor.logic.ability.AbilityCoolDownProc;
import logic.processor.logic.ability.AbilityProc;
import logic.processor.logic.ability.AbilityTriggerResetProc;
import logic.processor.logic.ability.BoostProc;
import logic.processor.logic.ability.ProjectileLauncherProc;
import logic.processor.logic.ability.TriggerRepeaterProc;
import logic.processor.logic.combat.resistance.ShieldProc;
import logic.processor.logic.combat.resistance.SpawnOnShieldDepletedProc;
import logic.processor.logic.command.NeededRotationProc;
import logic.processor.logic.command.NeededThrustProc;
import logic.processor.logic.command.PlayerAbilityControlProc;
import logic.processor.logic.command.PlayerRotationControlProc;
import logic.processor.logic.command.PlayerThrustControlProc;
import logic.processor.logic.holder.BoneHoldingProc;
import logic.processor.logic.holder.PlanarHoldingProc;
import logic.processor.logic.interaction.DestroyedOnTouchProc;
import logic.processor.logic.interaction.ShockwaveOnTouchProc;
import logic.processor.logic.interaction.SpawnMultipleOnBornProc;
import logic.processor.logic.interaction.SpawnOnDecayProc;
import logic.processor.logic.interaction.SpawnOnTouchProc;
import logic.processor.logic.interaction.TouchingClearingProc;
import logic.processor.logic.interaction.damage.DamageOnTouchProc;
import logic.processor.logic.interaction.damage.DamagingOverTimeProc;
import logic.processor.logic.interaction.damage.DamagingProc;
import logic.processor.logic.motion.RandomVelocityApplicationProc;
import logic.processor.logic.motion.VelocityApplicationProc;
import logic.processor.logic.motion.physic.DraggingProc;
import logic.processor.logic.motion.physic.PhysicForceProc;
import logic.processor.logic.motion.physic.RandomDraggingProc;
import logic.processor.logic.motion.physic.collisionDetection.CircleCircleCollisionProc;
import logic.processor.logic.motion.physic.collisionDetection.CircleEdgeCollisionProc;
import logic.processor.logic.motion.physic.collisionDetection.CollisionResolutionProc;
import logic.processor.logic.motion.physic.collisionDetection.EdgeEdgeCollisionProc;
import logic.processor.logic.senses.SightProc;
import logic.processor.logic.shipGear.AttritionProc;
import logic.processor.logic.shipGear.LightThrusterProc;
import logic.processor.logic.shipGear.RotationThrusterProc;
import logic.processor.logic.shipGear.ThrusterProc;
import logic.processor.logic.world.WorldProc;
import logic.processor.rendering.CameraPlacingProc;
import logic.processor.rendering.LightProc;
import logic.processor.rendering.MouseTargetingProc;
import logic.processor.rendering.audio.AbilityAudioProc;
import logic.processor.rendering.audio.AudioSourcePlacingProc;
import logic.processor.rendering.audio.AudioSourceProc;
import logic.processor.rendering.audio.ThrusterAudioProc;
import logic.processor.rendering.model.ModelPlacingProc;
import logic.processor.rendering.model.ModelProc;
import logic.processor.rendering.model.ModelRotationProc;
import logic.processor.rendering.particle.ParticlePlacingProc;
import logic.processor.rendering.particle.ParticleProc;
import logic.processor.rendering.particle.ParticleThrusterProc;
import logic.processor.rendering.sprite.SpritePlacingProc;
import logic.processor.rendering.sprite.SpriteProc;
import logic.processor.rendering.ui.FloatingLabelProc;
import logic.processor.rendering.ui.VelocityVisualisationProc;
import plugin.TopDownCamInputListener;
import plugin.circleCollisionShapeInstrument.CircleCollisionShapeInstrument;
import plugin.infiniteWorld.editor.view.WorldEditorTab;
import plugin.infiniteWorld.pager.regionPaging.RegionPager;
import plugin.infiniteWorld.world.WorldLocaliserState;
import plugin.planarStanceInstrument.PlanarStanceInstrument;
import util.geometry.geom2d.Point2D;

public class MainSample extends Alchemist {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	protected void onIntialize() {
		RendererPlatform.enqueue(() -> addRendererSystems());
		
		// adding scene view behavior to place blueprint in view with correct planar stance
		SceneViewBehavior.createEntityFunction = (blueprint, screenCoord) -> {
			RendererPlatform.enqueue(app -> {
				EntityData ed = app.getStateManager().getState(DataState.class).getEntityData(); 
				EntityId newEntity = blueprint.createEntity(ed, null);
				PlanarStance stance = ed.getComponent(newEntity, PlanarStance.class); 
				if(stance != null){
					Point2D planarCoord = app.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan(screenCoord);
					ed.setComponent(newEntity, new PlanarStance(planarCoord, stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
				}
			});
			return null;
		};
		
		// adding instruments
		new PlanarStanceInstrument();
		new CircleCollisionShapeInstrument();

		// adding the world editor window
		ViewPlatform.inspectorTabPane.getTabs().add(new WorldEditorTab());
		ViewPlatform.getSceneInputManager().removeListener(ViewPlatform.camera);
		ViewPlatform.getSceneInputManager().addListener(new TopDownCamInputListener());
		
		// adding the playtime listener
		ViewPlatform.setGameInputListener(new GameInputListener());
	}
	
	private boolean addRendererSystems(){
		Pipeline visualPipeline = EditorPlatform.getPipelineManager().createPipeline("visual", true, true);
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

		visualPipeline.addProcessor(new WorldProc());

		Pipeline audioPipeline = EditorPlatform.getPipelineManager().createPipeline("audio", true, false);
		audioPipeline.addProcessor(new AudioSourceProc());
		audioPipeline.addProcessor(new AudioSourcePlacingProc());
		audioPipeline.addProcessor(new ThrusterAudioProc());
		audioPipeline.addProcessor(new AbilityAudioProc());

		Pipeline commandPipeline = EditorPlatform.getPipelineManager().createPipeline("command", true, false);
		commandPipeline.addProcessor(new PlayerRotationControlProc());
		commandPipeline.addProcessor(new PlayerThrustControlProc());
		commandPipeline.addProcessor(new CameraPlacingProc());
		
		Pipeline logicPipeline = EditorPlatform.getPipelineManager().createPipeline("logic", false, false);
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
		
		// adding state to the renderer
		RendererPlatform.getStateManager().attach(new WorldLocaliserState());
		RendererPlatform.getStateManager().attach(new RegionPager());
		RendererPlatform.getStateManager().getState(RegionPager.class).setEnabled(true);
		RendererPlatform.getStateManager().attach(new MouseTargetingProc());


		return true;
	}
}
