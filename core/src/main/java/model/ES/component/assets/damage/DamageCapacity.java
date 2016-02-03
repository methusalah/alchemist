package model.ES.component.assets.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.math.Fraction;

public class DamageCapacity implements EntityComponent {
	private final DamageType type;
	private final int base;
	private final Fraction dotChance;
	private final int dotPerSecond;
	private final String blueprintOnImpact;
	private final String blueprintOnDOT;
	
	public DamageCapacity() {
		this.type = DamageType.BASIC; 
		this.base = 0;
		this.dotChance = new Fraction(0);
		this.dotPerSecond = 0;
		this.blueprintOnImpact = "";
		this.blueprintOnDOT = "";
	}

	public DamageCapacity(@JsonProperty("type")DamageType type,
			@JsonProperty("base")int base,
			@JsonProperty("dotChance")Fraction dotChance,
			@JsonProperty("dotPerSecond")int dotPerSecond,
			@JsonProperty("blueprintOnImpact")String blueprintOnImpact,
			@JsonProperty("blueprintOnDOT")String blueprintOnDOT) {
		this.type = type; 
		this.base = base;
		this.dotChance = dotChance;
		this.dotPerSecond = dotPerSecond;
		this.blueprintOnImpact = blueprintOnImpact;
		this.blueprintOnDOT = blueprintOnDOT;
	}

	public int getBase() {
		return base;
	}

	public DamageType getType() {
		return type;
	}

	public Fraction getDotChance() {
		return dotChance;
	}

	public int getDotPerSecond() {
		return dotPerSecond;
	}

	public String getBlueprintOnImpact() {
		return blueprintOnImpact;
	}

	public String getBlueprintOnDOT() {
		return blueprintOnDOT;
	}

}
