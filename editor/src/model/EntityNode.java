package model;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityId;

public class EntityNode {
	public final EntityId parent;
	public final String name; 
	public final List<EntityNode> children = new ArrayList<>();
	
	public EntityNode(EntityId parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
