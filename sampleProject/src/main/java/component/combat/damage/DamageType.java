package component.combat.damage;

public enum DamageType {
	BASIC ("Basic"),
	INCENDIARY ("Incendiary"),
	SHOCK ("Shock"),
	CORROSIVE ("Corrosive");

	private final String label;
	private DamageType(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}
}
