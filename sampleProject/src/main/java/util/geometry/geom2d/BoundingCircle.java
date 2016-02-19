package util.geometry.geom2d;

import java.util.ArrayList;

import util.geometry.geom2d.algorithm.Collider;
import util.math.AngleUtil;

/**
 *
 * @author Benoît
 */
public class BoundingCircle extends BoundingShape {
 
    public Point2D center;
    public double radius;
    
    public BoundingCircle(Point2D center, double radius){
        this.center = center;
        this.radius = radius;
    }
    
    @Override
    public boolean collide(BoundingShape shape){
    	return Collider.areColliding(this, shape);
    }
    
    public boolean contains(Point2D p){
        return p.getDistance(center) < radius;
    }
    
    public AlignedBoundingBox getABB(){
        ArrayList<Point2D> bounds = new ArrayList<>();
        bounds.add(center.getTranslation(0, radius));
        bounds.add(center.getTranslation(AngleUtil.RIGHT, radius));
        bounds.add(center.getTranslation(-AngleUtil.RIGHT, radius));
        bounds.add(center.getTranslation(AngleUtil.FLAT, radius));
        return new AlignedBoundingBox(bounds);
    }

    @Override
    public Point2D getCenter() {
        return new Point2D(center);
    }

    public double getRadius() {
        return radius;
    }
    
    
}
