package util.geometry.geom2d.algorithm;

import util.geometry.geom2d.AlignedBoundingBox;
import util.geometry.geom2d.BoundingCircle;
import util.geometry.geom2d.BoundingShape;
import util.geometry.geom2d.Point2D;

public class Container {
	
	public static boolean containsIn(BoundingShape container, BoundingShape contained){
		if(container instanceof AlignedBoundingBox && contained instanceof AlignedBoundingBox)
			return containsIn((AlignedBoundingBox)container, (AlignedBoundingBox)contained);
		else if(container instanceof AlignedBoundingBox && contained instanceof BoundingCircle)
			return containsIn((AlignedBoundingBox)container, (BoundingCircle)contained);
		else if(container instanceof BoundingCircle && contained instanceof AlignedBoundingBox)
			return containsIn((BoundingCircle)container, (AlignedBoundingBox)contained);
		else if(container instanceof BoundingCircle && contained instanceof BoundingCircle)
			return containsIn((BoundingCircle)container, (BoundingCircle)contained);
		else
			throw new IllegalArgumentException("Containement between " + container.getClass().getSimpleName() + " and " + contained.getClass().getSimpleName() + " is not yet supported.");

	}
	
	public static boolean containsIn(AlignedBoundingBox containerBox, AlignedBoundingBox containedBox){
		return containedBox.minX > containerBox.minX &&
				containedBox.maxX < containerBox.maxX &&
				containedBox.minY > containerBox.minY &&
				containedBox.maxY < containerBox.maxY;
	}

	public static boolean containsIn(BoundingCircle containerCircle, AlignedBoundingBox containedBox){
		for(Point2D p : containedBox.getPoints())
			if(p.getDistance(containerCircle.getCenter()) > containerCircle.getRadius())
				return false;
		return true;
	}
	
	public static boolean containsIn(AlignedBoundingBox containerBox, BoundingCircle containedCircle){
		return containsIn(containerBox, containedCircle.getABB());
	}

	public static boolean containsIn(BoundingCircle containerCircle, BoundingCircle containedCircle){
		return containerCircle.getCenter().getDistance(containedCircle.getCenter()) < containerCircle.getRadius()-containedCircle.getRadius();
	}
}
