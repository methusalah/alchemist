package model.ES.component.visuals;

import model.ES.richData.ParticleCaster;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class ParticleCasting implements EntityComponent{
	public final ParticleCaster caster;
	public final int actualPerSecond;
	
	public ParticleCasting(@JsonProperty("caster")ParticleCaster caster, @JsonProperty("actualPerSecond")int actualPerSecond) {
		this.caster = caster;
		this.actualPerSecond = actualPerSecond;
	}

	public ParticleCaster getCaster() {
		return caster;
	}

	public int getActualPerSecond() {
		return actualPerSecond;
	}

}
