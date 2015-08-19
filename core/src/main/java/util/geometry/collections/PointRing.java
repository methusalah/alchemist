package util.geometry.collections;

import java.util.Collection;

import util.geometry.geom2d.Point2D;


public class PointRing extends Ring<Point2D>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PointRing(Collection<Point2D> col) {
		super(col);
	}
	
	public PointRing() {
		
	}
}
