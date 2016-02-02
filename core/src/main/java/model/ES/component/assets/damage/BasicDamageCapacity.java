package model.ES.component.assets.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class BasicDamageCapacity implements EntityComponent {
	public final static double CORROSIVE_FLESH = 0.8;
	public final static double INCENDIARY_FLESH = 1.2;
	public final static double SHOCK_FLESH = 0.9;

	public final static double CORROSIVE_SHIELD = 0.8;
	public final static double INCENDIARY_SHIELD = 0.5;
	public final static double SHOCK_SHIELD = 1.5;

	public final static double CORROSIVE_ARMOR = 2;
	public final static double INCENDIARY_ARMOR = 0.8;
	public final static double SHOCK_ARMOR = 0.9;

	public enum DamageType {NORMAL, INCENDIARY, SHOCK, CORROSIVE, EXPLOSIVE}
	
	private final int base;

	private final double incendiaryChance;
	private final int incendiaryDmg;

	private final double shockChance;
	private final int shockDmg;
	
	private final double corrosiveChance;
	private final int corrosiveDmg;
	
	private final boolean isExplosive;
	
	public BasicDamageCapacity() {
		this.base = 0;
		this.incendiaryChance = 0;
		this.incendiaryDmg = 0;
		this.shockChance = 0;
		this.shockDmg = 0;
		this.corrosiveChance = 0;
		this.corrosiveDmg = 0;
		this.isExplosive = false;
	}

	public BasicDamageCapacity(@JsonProperty("base")int base,
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
