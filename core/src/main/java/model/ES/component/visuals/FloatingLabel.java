package model.ES.component.visuals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import model.ES.richData.ColorData;

public class FloatingLabel implements EntityComponent{
	private final String label;
	private final ColorData color;
	private final double size;
	
	public FloatingLabel() {
		this.label = "";
		this.color = new ColorData(0, 0, 0, 0);
		this.size = 0;
	}

	public FloatingLabel(@JsonProperty("label")String label,
			@JsonProperty("color")ColorData color,
			@JsonProperty("size")double size) {
		this.label = label;
		this.color = color;
		this.size = size;
	}

	public String getLabel() {
		return label;
	}

	public ColorData getColor() {
		return color;
	}

	public double getSize() {
		return size;
	}
}
