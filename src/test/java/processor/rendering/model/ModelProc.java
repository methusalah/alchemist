package processor.rendering.model;

import java.util.HashMap;
import java.util.Map;

import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;

import commonLogic.SpatialPool;
import component.assets.Model;
import model.ECS.builtInComponent.Naming;
import model.ECS.pipeline.Processor;
import model.tempImport.AppFacade;
import util.LogUtil;

public class ModelProc extends Processor {
	Map<String, Spatial> modelPrototypes = new HashMap<>();
	
	@Override
	protected void registerSets() {
		registerDefault(Model.class);
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		if(SpatialPool.models.keySet().contains(e.getId())){
			AppFacade.getMainSceneNode().detachChild(SpatialPool.models.get(e.getId()));
		}
	}

	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		if(SpatialPool.models.containsKey(e.getId()))
			AppFacade.getMainSceneNode().detachChild(SpatialPool.models.get(e.getId()));
		
		Model model = e.get(Model.class);
		
		Spatial s = getPrototype(model.path);
		
		if(s != null){
			s = s.clone();
			Naming n = entityData.getComponent(e.getId(), Naming.class);
			if(n != null)
				s.setName(n.getName());
			else
				s.setName("unnamed entity #"+e.getId());
			s.scale((float)model.scale);
			s.setUserData("EntityId", e.getId().getId());
			s.setShadowMode(ShadowMode.CastAndReceive);
			SpatialPool.models.put(e.getId(), s);
			AppFacade.getMainSceneNode().attachChild(s);
		}
	}
	
	private Spatial getPrototype(String modelPath) throws IllegalStateException {
		if(modelPath.isEmpty())
			return null;
		if (!modelPrototypes.containsKey(modelPath)) {
			try {
				Spatial s = AppFacade.getAssetManager().loadModel("models/" + modelPath);
				modelPrototypes.put(modelPath, s);
			} catch (Exception e) {
				LogUtil.warning("Model not found : models/" + modelPath + " ("+ e + ")");
				return null;
			}
		}
		return modelPrototypes.get(modelPath);
	}
	
}
