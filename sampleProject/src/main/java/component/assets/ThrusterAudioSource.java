package component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.math.Fraction;

public class ThrusterAudioSource implements EntityComponent {
	private final String startPath;
	private final String loopPath;
	private final String endPath;
	private final Fraction volume; 
	
	private long startTime = 0;
	private long loopTime = 0;
	
	
	public ThrusterAudioSource() {
		startPath = "";
		loopPath = "";
		endPath = "";
		volume = new Fraction(1);
	}
	
	public ThrusterAudioSource(@JsonProperty("startPath")String startPath,
			@JsonProperty("loopPath")String loopPath,
			@JsonProperty("endPath")String endPath,
			@JsonProperty("volume")Fraction volume) {
		this.startPath = startPath;
		this.loopPath = loopPath;
		this.endPath = endPath;
		this.volume = volume;
	}
	
	public String getStartPath() {
		return startPath;
	}

	public String getLoopPath() {
		return loopPath;
	}

	public String getEndPath() {
		return endPath;
	}

	public Fraction getVolume() {
		return volume;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(long loopTime) {
		this.loopTime = loopTime;
	}
}
