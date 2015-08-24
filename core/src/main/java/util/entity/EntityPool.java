package util.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.LogUtil;

public class EntityPool {
	
	private static long lastID = 0;
	private static List<Entity> entities = new ArrayList<>();
	private static Map<CompMask, EntityGroup> groups = new HashMap<>();

	private EntityPool(){
		
	}
	
	public static Entity createEntity(String name){
		Entity e = new Entity(lastID++, name);
		entities.add(e);
		return e;
	}

	public static Entity createEntity(){
		return createEntity(null);
	}
	
	public static void removeEntity(Entity e){
		entities.remove(e);
		for(EntityGroup g : findGroupsFor(e))
			g.remove(e);
	}
	private static List<Entity> getEntities(CompMask compSet){
		List<Entity> res = new ArrayList<>();
		for(Entity e : entities){
			if(e.has(compSet))
				res.add(e);
		}
		return res;
	}
	
	public static EntityGroup getGroup(CompMask compSet){
		if(!groups.containsKey(compSet))
			createGroup(compSet);
		return groups.get(compSet);
	}
	
	private static void createGroup(CompMask compSet){
		if(groups.containsKey(compSet))
			throw new RuntimeException("bad usage");
		EntityGroup g = new EntityGroup();
		g.addAll(getEntities(compSet));
		groups.put(compSet, g);
		if(compSet == null)
			LogUtil.info("comp set " + compSet);
	}
	
	protected static void removeFromGroups(Entity e){
		for(EntityGroup g : findGroupsFor(e))
			g.remove(e);
	}

	protected static void placeInGroups(Entity e){
		for(EntityGroup g : findGroupsFor(e))
			g.add(e);
	}
	
	private static List<EntityGroup> findGroupsFor(Entity e){
		List<EntityGroup> res = new ArrayList<>();
		for(CompMask set : groups.keySet()){
			if(set == null)
				LogUtil.info("zarb !");
			if(e.has(set))
				res.add(groups.get(set));
		}
		return res;
	}
	
	public static String toReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("EntityPool : " + entities.size() + " active entites, " + lastID + " entites created." + System.lineSeparator());
		sb.append("Groups : " + System.lineSeparator());
		for(CompMask set : groups.keySet()){
			sb.append("    " + set.toString() + ", nb entites : " + groups.get(set).size()+ System.lineSeparator());
			for(Entity e : groups.get(set))
				sb.append("        " + e.toString() + System.lineSeparator());
		}
		return sb.toString();
	}
}
