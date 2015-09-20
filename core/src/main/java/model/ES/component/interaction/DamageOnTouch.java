package model.ES.component.interaction;

import com.simsilica.es.EntityComponent;

public class DamageOnTouch implements EntityComponent {
	private final int amount;
	
	public DamageOnTouch(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}
	
	
}
