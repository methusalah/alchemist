package model.ES.component.visuals;

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class ParticleCaster implements EntityComponent{
	public static enum Facing {
		Horizontal, Velocity, Camera
	}
	public final String spritePath;
	public final int nbCol;
	public final int nbRow;
	public final double initialSpeed;
	public final double fanning;
	public final boolean randomSprite;
	public final int maxCount;
	public final int perSecond;
	public final double startSize;
	public final double endSize;
	public final Color startColor;
	public final Color endColor;
	public final double minLife;
	public final double maxLife;
	public final double rotationSpeed;
	public final boolean gravity;
	public final Facing facing;
	public final boolean add;
	public final double startVariation;
	public final boolean allAtOnce;
	
	public int actualPerSecond;
	
	public ParticleCaster(@JsonProperty("spritePath")String spritePath,
			@JsonProperty("nbCol")int nbCol,
			@JsonProperty("nbRow")int nbRow,
			@JsonProperty("initialSpeed")double initialSpeed,
			@JsonProperty("fanning")double fanning,
			@JsonProperty("randomSprite")boolean randomSprite,
			@JsonProperty("maxCount")int maxCount,
			@JsonProperty("perSecond")int perSecond,
			@JsonProperty("startSize")double startSize,
			@JsonProperty("endSize")double endSize,
			@JsonProperty("startColor")Color startColor,
			@JsonProperty("endColor")Color endColor,
			@JsonProperty("minLife")double minLife,
			@JsonProperty("maxLife")double maxLife,
			@JsonProperty("rotationSpeed")double rotationSpeed,
			@JsonProperty("gravity")boolean gravity,
			@JsonProperty("facing")Facing facing,
			@JsonProperty("add")boolean add,
			@JsonProperty("startVariation")double startVariation,
			@JsonProperty("allAtOnce")boolean allAtOnce
			) {
		this.spritePath = spritePath;
		this.nbCol = nbCol;
		this.nbRow = nbRow;
		this.initialSpeed = initialSpeed;
		this.fanning = fanning;
		this.randomSprite = randomSprite;
		this.maxCount = maxCount;
		this.perSecond = perSecond;
		this.startSize = startSize;
		this.endSize = endSize;
		this.startColor = startColor;
		this.endColor = endColor;
		this.minLife = minLife;
		this.maxLife = maxLife;
		this.rotationSpeed = rotationSpeed;
		this.gravity = gravity;
		this.facing = facing;
		this.add = add;
		this.startVariation = startVariation;
		this.allAtOnce = allAtOnce;
		this.actualPerSecond = perSecond;
	}

}
