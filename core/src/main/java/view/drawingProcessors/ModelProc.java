package view.drawingProcessors;

import java.util.HashMap;
import java.util.Map;

import model.ES.component.visuals.Model;
import util.LogUtil;
import view.SpatialPool;
import app.AppFacade;

import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class ModelProc extends Processor {
	Map<String, Spatial> modelPrototypes = new HashMap<>();
	
	@Override
	protected void registerSets() {
		registerDefault(Model.class);
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		if(SpatialPool.models.keySet().contains(e.getId()))
			AppFacade.getRootNode().detachChild(SpatialPool.models.remove(e.getId()));
	}

	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		if(SpatialPool.models.containsKey(e.getId()))
			AppFacade.getRootNode().detachChild(SpatialPool.models.get(e.getId()));
		
		Model model = e.get(Model.class);
		
		Spatial s = getPrototype(model.path);
		
		if(s != null){
			s = s.clone();
			s.scale((float)model.scale);
			SpatialPool.models.put(e.getId(), s);
			AppFacade.getRootNode().attachChild(s);
		}
	}
	
	private Spatial getPrototype(String modelPath) throws IllegalStateException {
		if(modelPath.isEmpty())
			return null;
		if (!modelPrototypes.containsKey(modelPath)) {
			try{
				Spatial s = AppFacade.getAssetManager().loadModel("models/" + modelPath);
				modelPrototypes.put(modelPath, s);
			} catch (Exception e) {
				LogUtil.warning("Model not found : models/" + modelPath);
				return null;
			}
		}
		return modelPrototypes.get(modelPath);
	}
	
}
