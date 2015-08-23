package util.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityGroup implements Iterable<Entity>{
	private final List<Entity> entities = new ArrayList<>();
	private final List<Entity> removed = new ArrayList<>();
	private final List<Entity> added = new ArrayList<>();
	private final List<Entity> updated = new ArrayList<>();
	 	
	protected void addAll(List<Entity> entites){
		this.entities.addAll(entites);
	}
 
	protected void remove(Entity e){
		if(entities.remove(e))
			removed.add(e);
	}
 
	protected void add(Entity e){
		if(!entities.contains(e)){
			entities.add(e);
			added.add(e);
		}
	}

	@Override
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}
	
	public int size(){
		return entities.size();
	}
	
	public List<Entity> getAdded(){
		return added;
	}
	
	public List<Entity> getRemoved(){
		return removed;
	}
	
	public List<Entity> getUpdated(){
		return updated;
	}
}
