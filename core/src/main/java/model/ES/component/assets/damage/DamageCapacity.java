package model.ES.component.assets.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class DamageCapacity implements EntityComponent {
	private final DamageType type;
	private final int base;
	private final double dotChance;
	private final int dotPerSecond;
	
	public DamageCapacity() {
		this.type = DamageType.BASIC; 
		this.base = 0;
		this.dotChance = 0;
		this.dotPerSecond = 0;
	}

	public DamageCapacity(@JsonProperty("type")DamageType type,
			@JsonProperty("base")int base,
			@JsonProperty("dotChance")double dotChance,
			@JsonProperty("dotPerSecond")int dotPerSecond) {
		this.type = type; 
		this.base = base;
		this.dotChance = dotChance;
		this.dotPerSecond = dotPerSecond;
	}

	public int getBase() {
		return base;
	}

	public DamageType getType() {
		return type;
	}

	public double getDotChance() {
		return dotChance;
	}

	public int getDotPerSecond() {
		return dotPerSecond;
	}

}
