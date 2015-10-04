package model.ES.component.shipGear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class TriggerRepeater implements EntityComponent {
	public final long start, nextPeriod;
	public final int maxDuration, period, periodRange;
	
	public TriggerRepeater(@JsonProperty("duration")int maxDuration,
			@JsonProperty("period")int period,
			@JsonProperty("periodRange")int periodRange,
			@JsonProperty("start")long start,
			@JsonProperty("nextPeriod")long nextPeriod) {
		this.maxDuration = maxDuration;
		this.period = period;
		this.periodRange = periodRange;
		this.start = start;
		this.nextPeriod = nextPeriod;
	}
}
