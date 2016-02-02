package model.ES.component.assets.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class IncendiaryDamageCapacity implements EntityComponent {
	public final static double INCENDIARY_FLESH = 1.2;
	public final static double INCENDIARY_SHIELD = 0.5;
	public final static double INCENDIARY_ARMOR = 0.8;

	private final int base;
	private final double dotChance;
	private final int dotDmg;

	public IncendiaryDamageCapacity() {
		this.base = 0;
		this.dotChance = 0;
		this.dotDmg = 0;
	}

	public IncendiaryDamageCapacity(@JsonProperty("base")int base,
			@JsonProperty("dotChance")double dotChance,
			@JsonProperty("dotDmg")int dotDmg) {
		this.base = base;
		this.dotChance = dotChance;
		this.dotDmg = dotDmg;
	}

	public int getBase() {
		return base;
	}

	public double getDotChance() {
		return dotChance;
	}

	public int getDotDmg() {
		return dotDmg;
	}
}
