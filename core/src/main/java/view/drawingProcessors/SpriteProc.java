package view.drawingProcessors;

import java.util.HashMap;
import java.util.Map;

import util.LogUtil;
import view.SpatialPool;
import view.jme.CenteredQuad;
import view.material.MaterialManager;
import app.AppFacade;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.visuals.Sprite;

public class SpriteProc extends Processor {
	Map<String, Spatial> spritePrototypes = new HashMap<>();
	
	@Override
	protected void registerSets() {
		registerDefault(Sprite.class);
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
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
		
		Sprite sprite = e.get(Sprite.class);
		
		Spatial s = getSprite(sprite.getPath());
		
		if(s != null){
			s = s.clone();
			s.scale((float)sprite.getSize());
			s.setUserData("EntityId", e.getId().getId());
			SpatialPool.models.put(e.getId(), s);
			AppFacade.getRootNode().attachChild(s);
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
				spritePrototypes.put(spritePath, g);
			} catch (IllegalStateException e) {
				LogUtil.warning("Texture not found : textures/" + spritePath);
				return null;
			}
		}
		return spritePrototypes.get(spritePath);
	}
	
}
