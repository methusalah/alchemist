package view.drawingProcessors;

import java.util.HashMap;
import java.util.Map;

import view.SpatialPool;
import model.ES.component.ModelComp;
import model.ES.component.motion.PlanarStance;
import app.AppFacade;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;

public class ModelProc extends Processor {
	Map<String, Spatial> modelPrototypes = new HashMap<>();
	
	@Override
	protected void registerSets() {
		register(ModelComp.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		createModel(e);
	}
	
	@Override
	protected void onEntityRemoved(Entity e, float elapsedTime) {
		SpatialPool.models.remove(e.getId());
	}

	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		createModel(e);
	}
	
	private Spatial getPrototype(String modelPath){
		if (!modelPrototypes.containsKey(modelPath)) {
			Spatial s = AppFacade.getAssetManager().loadModel("models/" + modelPath);
			modelPrototypes.put(modelPath, s);
		}
		return modelPrototypes.get(modelPath);
	}
	
	private void createModel(Entity e){
		ModelComp model = e.get(ModelComp.class);
		Spatial s = getPrototype(model.getPath()).clone();
		s.scale((float)model.getScale());
		model.setCreated();
		SpatialPool.models.put(e.getId(), s);
		app.getRootNode().attachChild(s);
	}
}
