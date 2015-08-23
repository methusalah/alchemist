package util.entity;


public class Processor {
	
	protected CompMask mask;
	protected EntityGroup entites;
	
	
	final void update(float elapsedTime){
		onUpdate(elapsedTime);
		for(Entity e : entites.getAdded())
			onEntityAdded(e);
		for(Entity e : entites.getRemoved())
			onEntityRemoved(e);
		for(Entity e : entites.getUpdated())
			onEntityUpdated(e);
	}
	
	
	final void setAttached(){
		entites = EntityPool.getGroup(mask);
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
