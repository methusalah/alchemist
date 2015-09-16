package view;

import java.util.HashMap;
import java.util.Map;

import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityId;

import model.ES.component.debug.VelocityDebug;
import view.jme.MyParticleEmitter;

public class SpatialPool {

	
	public static Map<EntityId, Spatial> models = new HashMap<>();
	public static Map<EntityId, Spatial> inertias = new HashMap<>();
	public static Map<EntityId, Spatial> appliedVelocities = new HashMap<>();
	public static Map<EntityId, Spatial> resultingVelocity = new HashMap<>();
	public static Map<EntityId, MyParticleEmitter> emitters = new HashMap<>();
	public static Map<VelocityDebug, Spatial> velocities = new HashMap<>();

	
	private SpatialPool(){
		
	}
}
