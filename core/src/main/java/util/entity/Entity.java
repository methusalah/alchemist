package util.entity;

import java.util.HashMap;
import java.util.Map;

public final class Entity {

	private final long id;
	private final String name;
	private final Map<Class<? extends Comp>, Comp> comps = new HashMap<>();
	
	protected Entity(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	 public <T extends Comp> void add(T comp) {
		if(contains(comp))
			throw new RuntimeException("Can't add the same component twice.");
		EntityPool.removeFromGroups(this);
		put(comp);
		EntityPool.placeInGroups(this);
	}

	public <T extends Comp> T get(Class<T> compClass){
		if(!contains(compClass))
			throw new RuntimeException("Can't find component.");
		return (T) comps.get(compClass);
	}
	
	public <T extends Comp> void remove(Class<? extends Comp> compClass) {
		if(!contains(compClass))
			throw new RuntimeException("Can't find component.");
		EntityPool.removeFromGroups(this);
		comps.remove(compClass);
		EntityPool.placeInGroups(this);
	}
	
	public <T extends Comp> void addOrUpdate(T comp){
//		if(!contains(comp))
//			throw new RuntimeException("Can't find component.");
		put(comp);
	}
	
	public boolean has(CompMask set){
		for(Class<? extends Comp> compClass : set)
			if(!contains(compClass))
					return false;
		return true;
	}

	private <T extends Comp> boolean contains(T comp){
		return contains(comp.getClass());
	}

	private boolean contains(Class<? extends Comp> compClass){
		return comps.containsKey(compClass);
	}
	
	private void put(Comp comp){
		comps.put(comp.getClass(), comp);
	}

	
	public String toString() {
		return Entity.class.getSimpleName() + " name: " + name + "/ID: " + id + " (" + new CompMask(comps.keySet()).toString() + ")";
	}
}
