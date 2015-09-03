package model.ES.component.holder;

import java.util.List;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class HolderOnBone implements EntityComponent{
	private final List<EntityId> holded;
	private final List<String> positionBones;
	private final List<String> directionBones;
	
	public HolderOnBone(List<EntityId> holded, List<String> positionBones, List<String> directionBones) {
		this.holded = holded;
		this.positionBones = positionBones;
		this.directionBones = directionBones;
	}

	public List<EntityId> getHolded() {
		return holded;
	}

	public List<String> getPositionBones() {
		return positionBones;
	}

	public List<String> getDirectionBones() {
		return directionBones;
	}
}
