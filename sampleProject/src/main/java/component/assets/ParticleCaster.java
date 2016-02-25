package component.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

import model.tempImport.ColorData;

public class ParticleCaster implements EntityComponent{
	public static enum Facing {
		Horizontal, Velocity, Camera
	}
	private final String spritePath;
	private final int nbCol;
	private final int nbRow;
	private final double initialSpeed;
	private final double fanning;
	private final boolean randomSprite;
	private final int maxCount;
	private final int perSecond;
	private final double startSize;
	private final double endSize;
	private final ColorData startColor;
	private final ColorData endColor;
	private final double minLife;
	private final double maxLife;
	private final double rotationSpeed;
	private final boolean gravity;
	private final Facing facing;
	private final boolean add;
	private final double startVariation;
	private final boolean allAtOnce;
	
	public ParticleCaster(){
		this.spritePath = "";
		this.nbCol = 0;
		this.nbRow = 0;
		this.initialSpeed = 0;
		this.fanning = 0;
		this.randomSprite = false;
		this.maxCount = 0;
		this.perSecond = 0;
		this.startSize = 0;
		this.endSize = 0;
		this.startColor = new ColorData(0, 0, 0, 0);
		this.endColor = new ColorData(0, 0, 0, 0);
		this.minLife = 0;
		this.maxLife = 0;
		this.rotationSpeed = 0;
		this.gravity = false;
		this.facing = Facing.Camera;
		this.add = false;
		this.startVariation = 0;
		this.allAtOnce = false;
	}
	
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
			@JsonProperty("startColor")ColorData startColor,
			@JsonProperty("endColor")ColorData endColor,
			@JsonProperty("minLife")double minLife,
			@JsonProperty("maxLife")double maxLife,
			@JsonProperty("rotationSpeed")double rotationSpeed,
			@JsonProperty("gravity")boolean gravity,
			@JsonProperty("facing")Facing facing,
			@JsonProperty("add")boolean add,
			@JsonProperty("startVariation")double startVariation,
			@JsonProperty("allAtOnce")boolean allAtOnce) {
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
	}

	public String getSpritePath() {
		return spritePath;
	}

	public int getNbCol() {
		return nbCol;
	}

	public int getNbRow() {
		return nbRow;
	}

	public double getInitialSpeed() {
		return initialSpeed;
	}

	public double getFanning() {
		return fanning;
	}

	public boolean isRandomSprite() {
		return randomSprite;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public int getPerSecond() {
		return perSecond;
	}

	public double getStartSize() {
		return startSize;
	}

	public double getEndSize() {
		return endSize;
	}

	public ColorData getStartColor() {
		return startColor;
	}

	public ColorData getEndColor() {
		return endColor;
	}

	public double getMinLife() {
		return minLife;
	}

	public double getMaxLife() {
		return maxLife;
	}

	public double getRotationSpeed() {
		return rotationSpeed;
	}

	public boolean isGravity() {
		return gravity;
	}

	public Facing getFacing() {
		return facing;
	}

	public boolean isAdd() {
		return add;
	}

	public double getStartVariation() {
		return startVariation;
	}

	public boolean isAllAtOnce() {
		return allAtOnce;
	}
}
