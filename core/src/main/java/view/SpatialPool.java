package view;

import java.util.HashMap;
import java.util.Map;

import com.jme3.scene.Spatial;
import com.simsilica.es.EntityId;

public class SpatialPool {

	
	public static Map<EntityId, Spatial> models = new HashMap<>();

	
	private SpatialPool(){
		
	}
}
