package model.world.terrain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TerrainTexture {
	private final String diffuse;
	private final String normal;
	private final double scale;
	
	public TerrainTexture(@JsonProperty("diffuse")String diffuse,
			@JsonProperty("normal")String normal,
			@JsonProperty("scale")double scale) {
		this.diffuse = diffuse;
		this.normal = normal;
		this.scale = scale;
	}

	public String getDiffuse() {
		return diffuse;
	}

	public String getNormal() {
		return normal;
	}

	public double getScale() {
		return scale;
	}
}
