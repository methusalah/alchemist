package model.ES.component.visuals;

import java.util.HashMap;

import com.simsilica.es.EntityComponent;

import util.geometry.geom3d.Point3D;

public class Skeleton implements EntityComponent{
	private final HashMap<String, Point3D> bonePositions;
	private final HashMap<String, Point3D> boneDirections;
	private final boolean initialized;
	
	public Skeleton(HashMap<String, Point3D> bonePositions, HashMap<String, Point3D> boneDirections, boolean initialized) {
		this.bonePositions = bonePositions;
		this.boneDirections = boneDirections;
		this.initialized = initialized;
	}

	public Point3D getPosition(String boneName) {
		return bonePositions.get(boneName);
	}
	public Point3D getDirection(String boneName) {
		return boneDirections.get(boneName);
	}
	
	public HashMap<String, Point3D> getBonePositions() {
		return bonePositions;
	}
	public HashMap<String, Point3D> getBoneDirections() {
		return boneDirections;
	}

	public boolean isInitialized() {
		return initialized;
	}

}
