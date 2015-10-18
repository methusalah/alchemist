package model.ES.component.audio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.math.Angle;
import util.math.Fraction;

public class AudioSource implements EntityComponent {
	private final String path;
	private final boolean loop;
	private final Fraction volume; 
	
	
	public AudioSource() {
		path = "";
		loop = false;
		volume = new Fraction(1);
	}
	
	public AudioSource(@JsonProperty("path")String path,
			@JsonProperty("loop")boolean loop,
			@JsonProperty("volume")Fraction volume) {
		this.path = path;
		this.loop = loop;
		this.volume = volume;
	}
	
	public String getPath() {
		return path;
	}

	public boolean isLoop() {
		return loop;
	}

	public Fraction getVolume() {
		return volume;
	}
}
