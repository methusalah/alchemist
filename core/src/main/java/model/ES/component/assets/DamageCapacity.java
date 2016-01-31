package model.ES.component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class DamageCapacity implements EntityComponent {
	private final int base;

	private final double incendiaryChance;
	private final int incendiaryDmg;

	private final double shockChance;
	private final int shockDmg;
	
	private final double corrosiveChance;
	private final int corrosiveDmg;
	
	private final boolean isExplosive;
	
	public DamageCapacity() {
		this.base = 0;
		this.incendiaryChance = 0;
		this.incendiaryDmg = 0;
		this.shockChance = 0;
		this.shockDmg = 0;
		this.corrosiveChance = 0;
		this.corrosiveDmg = 0;
		this.isExplosive = false;
	}

	public DamageCapacity(@JsonProperty("base")int base,
			@JsonProperty("incendiaryChance")double incendiaryChance,
			@JsonProperty("incendiaryDmg")int incendiaryDmg,
			@JsonProperty("shockChance")double shockChance,
			@JsonProperty("shockDmg")int shockDmg,
			@JsonProperty("corrosiveChance")double corrosiveChance,
			@JsonProperty("corrosiveDmg")int corrosiveDmg,
			@JsonProperty("isExplosive")boolean isExplosive) {
		this.base = base;
		this.incendiaryChance = incendiaryChance;
		this.incendiaryDmg = incendiaryDmg;
		this.shockChance = shockChance;
		this.shockDmg = shockDmg;
		this.corrosiveChance = corrosiveChance;
		this.corrosiveDmg = corrosiveDmg;
		this.isExplosive = isExplosive;
	}

	public int getBase() {
		return base;
	}

	public double getIncendiaryChance() {
		return incendiaryChance;
	}

	public int getIncendiaryDmg() {
		return incendiaryDmg;
	}

	public double getShockChance() {
		return shockChance;
	}

	public int getShockDmg() {
		return shockDmg;
	}

	public double getCorrosiveChance() {
		return corrosiveChance;
	}

	public int getCorrosiveDmg() {
		return corrosiveDmg;
	}

	public boolean isExplosive() {
		return isExplosive;
	}
	
	
}
