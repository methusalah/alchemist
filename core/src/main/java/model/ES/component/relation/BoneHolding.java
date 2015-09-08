package model.ES.component.relation;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class BoneHolding implements EntityComponent{
	private final EntityId holder;

	private final String positionBoneName;
	private final String directionBoneName;
	
	public BoneHolding(EntityId holder, String positionBoneName, String directionBoneName) {
		this.holder = holder;
		this.positionBoneName = positionBoneName;
		this.directionBoneName = directionBoneName;
	}

	public EntityId getHolder() {
		return holder;
	}

	public String getPositionBoneName() {
		return positionBoneName;
	}

	public String getDirectionBoneName() {
		return directionBoneName;
	}
}
