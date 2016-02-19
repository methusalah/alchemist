package processor.rendering.sprite;

import java.util.HashMap;
import java.util.Map;

import com.jme3.material.RenderState.BlendMode;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;

import commonLogic.CenteredQuad;
import commonLogic.SpatialPool;
import component.assets.Sprite;
import model.ECS.pipeline.Processor;
import model.tempImport.AppFacade;
import util.LogUtil;
import view.MaterialManager;

public class SpriteProc extends Processor {
	Map<String, Spatial> spritePrototypes = new HashMap<>();
	
	@Override
	protected void registerSets() {
		registerDefault(Sprite.class);
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		if(SpatialPool.models.containsKey(e.getId()))
			AppFacade.getMainSceneNode().detachChild(SpatialPool.models.remove(e.getId()));
	}

	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		if(SpatialPool.models.containsKey(e.getId()))
			AppFacade.getMainSceneNode().detachChild(SpatialPool.models.get(e.getId()));
		
		Sprite sprite = e.get(Sprite.class);
		
		Spatial s = getSprite(sprite.getPath());
		
		if(s != null){
			s = s.clone();
			s.scale((float)sprite.getSize());
			s.setUserData("EntityId", e.getId().getId());
			SpatialPool.models.put(e.getId(), s);
			AppFacade.getMainSceneNode().attachChild(s);
		}
	}
	
	private Spatial getSprite(String spritePath){
		if(spritePath.isEmpty())
			return null;
		if (!spritePrototypes.containsKey(spritePath)) {
			try{
				Geometry g = new Geometry("Sprite");
				g.setMesh(new CenteredQuad(1, 1));
				g.setMaterial(MaterialManager.getLightingTexture("textures/"+spritePath));
				g.getMaterial().setBoolean("UseAlpha",true);
				g.setQueueBucket(Bucket.Transparent);
				g.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

				spritePrototypes.put(spritePath, g);
			} catch (IllegalStateException e) {
				LogUtil.warning("Texture not found : textures/" + spritePath);
				return null;
			}
		}
		return spritePrototypes.get(spritePath);
	}
	
}
