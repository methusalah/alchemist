package model.ES.component.ability;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class TriggerRepeater implements EntityComponent {
	private final int remainingDuration, remainingBeforePeriod;
	private final int maxDuration, period, periodRange;
	
	public TriggerRepeater() {
		remainingDuration = 0;
		remainingBeforePeriod = 0;
		maxDuration = 0;
		period = 0;
		periodRange = 0;
	}
	
	public TriggerRepeater(@JsonProperty("duration")int maxDuration,
			@JsonProperty("period")int period,
			@JsonProperty("periodRange")int periodRange,
			@JsonProperty("remainingDuration")int remainingDuration,
			@JsonProperty("remainingBeforePeriod")int remainingBeforePeriod) {
		this.maxDuration = maxDuration;
		this.period = period;
		this.periodRange = periodRange;
		this.remainingDuration = remainingDuration;
		this.remainingBeforePeriod = remainingBeforePeriod;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public int getPeriod() {
		return period;
	}

	public int getPeriodRange() {
		return periodRange;
	}

	public int getRemainingDuration() {
		return remainingDuration;
	}

	public int getRemainingBeforePeriod() {
		return remainingBeforePeriod;
	}
}
