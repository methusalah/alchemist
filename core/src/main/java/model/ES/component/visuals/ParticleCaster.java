package model.ES.component.visuals;

import java.awt.Color;

import model.battlefield.actors.ParticleActor.Facing;

import com.simsilica.es.EntityComponent;

import util.geometry.geom3d.Point3D;

public class ParticleCaster implements EntityComponent{
	public static enum Facing {
		Horizontal, Velocity, Camera
	}

	private final Point3D translation;
	private final Point3D direction;

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
	private final Color startColor;
	private final Color endColor;
	private final double minLife;
	private final double maxLife;
	private final double rotationSpeed;
	private final boolean gravity;
	private final Facing facing;
	private final boolean add;
	private final double startVariation;
	private final boolean allAtOnce;
	
	public ParticleCaster(Point3D translation,
			Point3D directionVector,
			String spritePath,
			int nbCol,
			int nbRow,
			double initialSpeed,
			double fanning,
			boolean randomSprite,
			int maxCount,
			int perSecond,
			double startSize,
			double endSize,
			Color startColor,
			Color endColor,
			double minLife,
			double maxLife,
			double rotationSpeed,
			boolean gravity,
			Facing facing,
			boolean add,
			double startVariation,
			boolean allAtOnce
			) {
		this.translation = translation;
		this.direction = directionVector;
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
	
	

	public double getFanning() {
		return fanning;
	}

	public double getInitialSpeed() {
		return initialSpeed;
	}

	public Point3D getTranslation() {
		return translation;
	}

	public Point3D getDirection() {
		return direction;
	}

	public double getPerSecond() {
		return perSecond;
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

	public boolean isRandomSprite() {
		return randomSprite;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public double getStartSize() {
		return startSize;
	}

	public double getEndSize() {
		return endSize;
	}

	public Color getStartColor() {
		return startColor;
	}

	public Color getEndColor() {
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
