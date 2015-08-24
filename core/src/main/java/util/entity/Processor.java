package util.entity;

import java.util.List;


public class Processor {
	
	protected CompMask mask;
	protected EntityGroup entities;
	
	
	final void update(float elapsedTime){
		onUpdate(elapsedTime);
		for(Entity e : entities.getAdded())
			onEntityAdded(e);
		for(Entity e : entities.getRemoved())
			onEntityRemoved(e);
		for(Entity e : entities.getUpdated())
			onEntityUpdated(e);
	}
	
	
	final void setAttached(){
		entities = EntityPool.getGroup(mask);
		onAttached();
	}
	final void setDetached(){
//		EntityPool.unregisterFromGroup();
		onDetached();
	}
	
	public void enable(){
		
	}

	public void disable(){
		
	}
	
	
	
	
	
	
	
	protected void onAttached(){
		
	}

	protected void onDetached(){
		
	}
	
	protected void onUpdate(float elapsedTime){
		
	}
	
	protected void onEntityAdded(Entity e){
		
	}
	
	protected void onEntityRemoved(Entity e){
		
	}
	
	protected void onEntityUpdated(Entity e){
		
	}
	
	
}
