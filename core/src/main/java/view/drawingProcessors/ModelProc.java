package view.drawingProcessors;

import java.util.HashMap;
import java.util.Map;

import util.LogUtil;
import view.SpatialPool;
import model.ES.component.motion.PlanarStance;
import model.ES.component.visuals.Model;
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
		register(Model.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		createModel(e);
	}
	
	@Override
	protected void onEntityRemoved(Entity e, float elapsedTime) {
		AppFacade.getRootNode().detachChild(SpatialPool.models.remove(e.getId()));
		
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
		if(SpatialPool.models.containsKey(e.getId()))
			AppFacade.getRootNode().detachChild(SpatialPool.models.get(e.getId()));
		
		Model model = e.get(Model.class);
		LogUtil.info("model creation "+model.path);
		Spatial s = getPrototype(model.path).clone();
		s.scale((float)model.scale);
		model.created = true;
		SpatialPool.models.put(e.getId(), s);
		AppFacade.getRootNode().attachChild(s);
	}
}
