package app;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import ECS.component.motion.PlanarStance;
import ECS.processor.logic.ChasingCameraProc;
import ECS.processor.logic.LifeTimeProc;
import ECS.processor.logic.ParentingCleanerProc;
import ECS.processor.logic.RemovedCleanerProc;
import ECS.processor.logic.RemoverProc;
import ECS.processor.logic.AI.BehaviorTreeProc;
import ECS.processor.logic.ability.AbilityCoolDownProc;
import ECS.processor.logic.ability.AbilityProc;
import ECS.processor.logic.ability.AbilityTriggerResetProc;
import ECS.processor.logic.ability.BoostProc;
import ECS.processor.logic.ability.ProjectileLauncherProc;
import ECS.processor.logic.ability.TriggerRepeaterProc;
import ECS.processor.logic.combat.resistance.ShieldProc;
import ECS.processor.logic.combat.resistance.SpawnOnShieldDepletedProc;
import ECS.processor.logic.command.NeededRotationProc;
import ECS.processor.logic.command.NeededThrustProc;
import ECS.processor.logic.command.PlayerAbilityControlProc;
import ECS.processor.logic.command.PlayerRotationControlProc;
import ECS.processor.logic.command.PlayerThrustControlProc;
import ECS.processor.logic.holder.BoneHoldingProc;
import ECS.processor.logic.holder.PlanarHoldingProc;
import ECS.processor.logic.interaction.DestroyedOnTouchProc;
import ECS.processor.logic.interaction.ShockwaveOnTouchProc;
import ECS.processor.logic.interaction.SpawnMultipleOnBornProc;
import ECS.processor.logic.interaction.SpawnOnDecayProc;
import ECS.processor.logic.interaction.SpawnOnTouchProc;
import ECS.processor.logic.interaction.TouchingClearingProc;
import ECS.processor.logic.interaction.damage.DamageOnTouchProc;
import ECS.processor.logic.interaction.damage.DamagingOverTimeProc;
import ECS.processor.logic.interaction.damage.DamagingProc;
import ECS.processor.logic.motion.RandomVelocityApplicationProc;
import ECS.processor.logic.motion.VelocityApplicationProc;
import ECS.processor.logic.motion.physic.DraggingProc;
import ECS.processor.logic.motion.physic.PhysicForceProc;
import ECS.processor.logic.motion.physic.RandomDraggingProc;
import ECS.processor.logic.motion.physic.collisionDetection.CircleCircleCollisionProc;
import ECS.processor.logic.motion.physic.collisionDetection.CircleEdgeCollisionProc;
import ECS.processor.logic.motion.physic.collisionDetection.CollisionResolutionProc;
import ECS.processor.logic.motion.physic.collisionDetection.EdgeEdgeCollisionProc;
import ECS.processor.logic.senses.SightProc;
import ECS.processor.logic.shipGear.AttritionProc;
import ECS.processor.logic.shipGear.LightThrusterProc;
import ECS.processor.logic.shipGear.RotationThrusterProc;
import ECS.processor.logic.shipGear.ThrusterProc;
import ECS.processor.logic.world.WorldProc;
import ECS.processor.rendering.CameraPlacingProc;
import ECS.processor.rendering.LightProc;
import ECS.processor.rendering.MouseTargetingProc;
import ECS.processor.rendering.audio.AbilityAudioProc;
import ECS.processor.rendering.audio.AudioSourcePlacingProc;
import ECS.processor.rendering.audio.AudioSourceProc;
import ECS.processor.rendering.audio.ThrusterAudioProc;
import ECS.processor.rendering.model.ModelPlacingProc;
import ECS.processor.rendering.model.ModelProc;
import ECS.processor.rendering.model.ModelRotationProc;
import ECS.processor.rendering.particle.ParticlePlacingProc;
import ECS.processor.rendering.particle.ParticleProc;
import ECS.processor.rendering.particle.ParticleThrusterProc;
import ECS.processor.rendering.sprite.SpritePlacingProc;
import ECS.processor.rendering.sprite.SpriteProc;
import ECS.processor.rendering.ui.FloatingLabelProc;
import ECS.processor.rendering.ui.VelocityVisualisationProc;
import command.GameInputListener;
import model.EditorPlatform;
import model.ECS.pipeline.Pipeline;
import model.state.DataState;
import model.state.SceneSelectorState;
import model.tempImport.RendererPlatform;
import plugin.TopDownCamInputListener;
import plugin.circleCollisionShapeInstrument.CircleCollisionShapeInstrument;
import plugin.infiniteWorld.editor.view.WorldEditorTab;
import plugin.infiniteWorld.pager.regionPaging.RegionPager;
import plugin.infiniteWorld.world.WorldLocaliserState;
import plugin.planarStanceInstrument.PlanarStanceInstrument;
import presentation.sceneView.SceneViewBehavior;
import util.geometry.geom2d.Point2D;
import view.Alchemist;
import view.ViewPlatform;

public class mainTest extends Alchemist {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	protected void onIntialize() {
		RendererPlatform.enqueue(() -> addRendererSystems());
		
		// adding scene view behavior to place blueprint in view with correct planar stance
		SceneViewBehavior.createEntityFunction = (blueprint, screenCoord) -> {
			EditorPlatform.getScene().enqueue(app -> {
				EntityData ed = app.getStateManager().getState(DataState.class).getEntityData(); 
				EntityId newEntity = blueprint.createEntity(ed, null);
				PlanarStance stance = ed.getComponent(newEntity, PlanarStance.class); 
				if(stance != null){
					Point2D planarCoord = app.getStateManager().getState(SceneSelectorState.class).getPointedCoordInPlan(screenCoord);
					ed.setComponent(newEntity, new PlanarStance(planarCoord, stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
				}
				return true;
			});
			return null;
		};
		
		// adding instruments
		new PlanarStanceInstrument(EditorPlatform.getScene());
		new CircleCollisionShapeInstrument(EditorPlatform.getScene());

		// adding the world editor window
		ViewPlatform.inspectorTabPane.getTabs().add(new WorldEditorTab());
		ViewPlatform.getSceneInputManager().removeListener(ViewPlatform.camera);
		ViewPlatform.getSceneInputManager().addListener(new TopDownCamInputListener(EditorPlatform.getScene()));
		
		// adding the playtime listener
		ViewPlatform.game = new GameInputListener(EditorPlatform.getScene());
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
