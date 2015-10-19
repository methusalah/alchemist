package model.ES.component.visuals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import util.math.Angle;

public class Sprite implements EntityComponent {
	private final String path;
	private final double size;
	
	public Sprite() {
		path = "";
		size = 1;
	}
	public Sprite(@JsonProperty("path")String path,
			@JsonProperty("size")double size) {
		this.path = path;
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public double getSize() {
		return size;
	}
}
