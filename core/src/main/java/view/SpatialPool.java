package view;

import java.util.HashMap;
import java.util.Map;

import com.jme3.effect.ParticleEmitter;
import com.jme3.light.Light;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityId;

import model.ES.richData.VelocityView;
import util.LogUtil;
import view.jme.MyParticleEmitter;

public class SpatialPool {

	
	public static final Map<EntityId, Spatial> models = new HashMap<>();
	public static final Map<EntityId, Spatial> inertias = new HashMap<>();
	public static final Map<EntityId, Spatial> appliedVelocities = new HashMap<>();
	public static final Map<EntityId, Spatial> resultingVelocity = new HashMap<>();
	public static final Map<EntityId, MyParticleEmitter> emitters = new HashMap<>();
	public static final Map<EntityId, Light> lights = new HashMap<>();
	public static final Map<VelocityView, Spatial> velocities = new HashMap<>();
	
	private SpatialPool(){
		
	}
}
