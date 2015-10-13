package model.ES.component.relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class BoneHolding implements EntityComponent{
	public final EntityId holder;
	public final String positionBoneName;
	public final String directionBoneName;
	
	public BoneHolding(@JsonProperty("holder")EntityId holder,
			@JsonProperty("positionBoneName")String positionBoneName,
			@JsonProperty("directionBoneName")String directionBoneName) {
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
