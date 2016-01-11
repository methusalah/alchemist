package presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsilica.es.EntityComponent;

import application.EditorPlatform;
import model.ES.component.Cooldown;
import model.ES.component.LifeTime;
import model.ES.component.Naming;
import model.ES.component.ToRemove;
import model.ES.component.assets.Ability;
import model.ES.component.assets.AbilityTrigger;
import model.ES.component.assets.Attackable;
import model.ES.component.assets.Attrition;
import model.ES.component.assets.Boost;
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
import model.ES.component.motion.SpaceStance;
import model.ES.component.motion.physic.CircleCollisionShape;
import model.ES.component.motion.physic.Collisioning;
import model.ES.component.motion.physic.Dragging;
import model.ES.component.motion.physic.EdgedCollisionShape;
import model.ES.component.motion.physic.Physic;
import model.ES.component.motion.physic.PhysicForce;
import model.ES.component.motion.physic.RandomDragging;
import model.ES.component.visuals.Lighting;
import model.ES.component.visuals.ModelRotation;
import model.ES.component.visuals.ParticleCaster;
import model.ES.component.visuals.Skeleton;
import model.ES.component.visuals.Sprite;
import model.ES.component.world.PopulationTooling;
import model.ES.component.world.TerrainTooling;
import util.LogUtil;
import util.event.EventManager;

public class InspectorPresenter {
	private final Map<String, Class<? extends EntityComponent>> componentClasses = new HashMap<>();
	
	private EntityNode en;
	
	public InspectorPresenter() {
		en = EditorPlatform.getSelectionProperty().getValue();
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> en = newValue);
		
		EventManager.register(this);
		addUserComponent(Naming.class,
				PlanarStance.class,
				SpaceStance.class,
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
				PopulationTooling.class,
				TerrainTooling.class,
				Boost.class,
				CircleCollisionShape.class,
				EdgedCollisionShape.class);
	}
	
	public void updateComponent(EntityComponent comp, String propertyName, Object value){
		// In this piece of code, we can't just change the component's field because it's imutable
		// we serialize the whole component into a jsontree, change the value in the tree, then
		// deserialize in a new component, that we will be able to attach to entity the proper way
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.valueToTree(EditorPlatform.getEntityData().getComponent(en.getEntityId(), comp.getClass()));
		Iterator<Entry<String, JsonNode>> i = n.fields();
		while (i.hasNext()) {
			Entry<String, JsonNode> entry = (Entry<String, JsonNode>) i.next();
			if(entry.getKey().equals(propertyName)){
				entry.setValue(mapper.valueToTree(value));
				break;
			}
		}
		
		EntityComponent newComp = null;
		try {
			newComp = new ObjectMapper().treeToValue(n, comp.getClass());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
			
		EditorPlatform.getEntityData().setComponent(en.getEntityId(), newComp);
	}
	
	@SafeVarargs
	private final void addUserComponent(Class<? extends EntityComponent> ... compClasses){
		for(Class<? extends EntityComponent> compClass : compClasses)
			componentClasses.put(compClass.getSimpleName(), compClass);
	}

	public List<String> getComponentNames(){
		List<String> res = new ArrayList<String>(componentClasses.keySet());
		Collections.sort(res);
		return res;
	}

	public void addComponent(String componentName){
		try {
			EntityComponent comp = componentClasses.get(componentName).newInstance();
			EditorPlatform.getEntityData().setComponent(en.getEntityId(), comp);
		} catch (InstantiationException | IllegalAccessException e) {
			LogUtil.warning("Can't instanciate component "+componentName);
		}
	}
	public void removeComponent(Class<? extends EntityComponent> componentClass){
		EntityComponent comp = null;
		for(EntityComponent c : en.componentListProperty())
			if(c.getClass() == componentClass){
				comp = c;
				break;
			}
		if(comp == null)
			throw new IllegalArgumentException("trying to remove a component ("+componentClass.getSimpleName()+") that doesn't exist on current entity");
		en.componentListProperty().remove(comp);
		EditorPlatform.getEntityData().removeComponent(en.getEntityId(), componentClass);
	}
}
