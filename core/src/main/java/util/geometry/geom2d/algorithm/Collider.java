package util.geometry.geom2d.algorithm;

import util.geometry.geom2d.AlignedBoundingBox;
import util.geometry.geom2d.BoundingCircle;
import util.geometry.geom2d.BoundingShape;
import util.geometry.geom2d.Point2D;
import util.geometry.geom2d.Segment2D;

public class Collider {

	public static boolean areColliding(BoundingShape s1, BoundingShape s2){
		if(s1 instanceof AlignedBoundingBox && s2 instanceof AlignedBoundingBox)
			return areColliding((AlignedBoundingBox)s1, (AlignedBoundingBox)s2);
		else if(s1 instanceof AlignedBoundingBox && s2 instanceof BoundingCircle)
			return areColliding((AlignedBoundingBox)s1, (BoundingCircle)s2);
		else if(s1 instanceof BoundingCircle && s2 instanceof AlignedBoundingBox)
			return areColliding((BoundingCircle)s1, (AlignedBoundingBox)s2);
		else if(s1 instanceof BoundingCircle && s2 instanceof BoundingCircle)
			return areColliding((BoundingCircle)s1, (BoundingCircle)s2);
		else
			throw new IllegalArgumentException("Collision between " + s1.getClass().getSimpleName() + " and " + s2.getClass().getSimpleName() + " is not yet supported.");
	}

	public static boolean areColliding(AlignedBoundingBox box1, AlignedBoundingBox box2){
		return shareX(box1, box2) && shareY(box1, box2);
	}

	public static boolean areColliding(BoundingCircle circle, AlignedBoundingBox box){
		return areColliding(box,  circle);
	}
	
	public static boolean areColliding(AlignedBoundingBox box, BoundingCircle circle){
        // first we check collision between boxes
        if(box.collide(circle.getABB())){
            // first case : the box has one of its sum inside the circle
            for(Point2D p : box.getPoints())
                if(circle.contains(p))
                    return true;
            
            // second case : the box contains the center of the circle
            if(box.contains(circle.getCenter()))
                return true;
            
            // third case : the projection of the center is on one of the box segment
            Segment2D hor = box.getEdges().get(0);
            Segment2D vert = box.getEdges().get(1);
            if(hor.containsProjected(circle.getCenter()) ||
                    vert.containsProjected(circle.getCenter()))
                return true;
            
            // if not, the bounding box of the circle touch, but the circle itself doesn't
        }
        return false;
	}

	public static boolean areColliding(BoundingCircle c1, BoundingCircle c2){
		return c1.getCenter().getDistance(c2.getCenter()) < c1.getRadius()+c2.getRadius();
	}

	
	public static boolean shareX(AlignedBoundingBox box1, AlignedBoundingBox box2) {
		return  box1.isInXBounds(box2.maxX) || box1.isInXBounds(box2.minX) || box1.isInXBounds(box2.maxX) || box1.isInXBounds(box2.minX);
	}

	public static boolean shareY(AlignedBoundingBox box1, AlignedBoundingBox box2) {
		return  box1.isInYBounds(box2.maxY) || box1.isInYBounds(box2.minY) || box1.isInYBounds(box2.maxY) || box1.isInYBounds(box2.minY);
	}


}
