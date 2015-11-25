package util.geometry.geom2d.algorithm;

import util.geometry.collections.Map2D;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.RandomUtil;


public class PerlinNoise {
	
	public static int RESOLUTION = 200;
	
	Map2D<Point2D> gradients;
	
	public PerlinNoise() {
		gradients = new Map2D<>(RESOLUTION, RESOLUTION);
		for(int i = 0; i < RESOLUTION*RESOLUTION; i++)
			gradients.set(i, Point2D.ORIGIN.getTranslation(RandomUtil.next()*AngleUtil.FULL, 1));
	}

	
	public double noise(Point2D p, int octaves, double persistence) {
	    double total = 0;
	    double frequency = 1;
	    double amplitude = 1;
	    double maxValue = 0;  // Used for normalizing result to 0.0 - 1.0
	    for(int i = 0; i < octaves; i++) {
	        total += noise(p.getMult(frequency)) * amplitude;
	        
	        maxValue += amplitude;
	        
	        amplitude *= persistence;
	        frequency *= 2;
	    }
	    return total/maxValue;
	}
	
	public double noise(Point2D p){
		Point2D pInMapSpace = inMapSpace(p);
		
		int x = (int)pInMapSpace.x;
		int y = (int)pInMapSpace.y;
		
		Point2D sw = new Point2D(x, y);
		Point2D se = new Point2D(x+1, y);
		Point2D nw = new Point2D(x, y+1);
		Point2D ne = new Point2D(x+1, y+1);
		
		Point2D g1 = gradients.get(inMapSpace(p));
		Point2D g2 = gradients.get(inMapSpace(p.getAddition(1, 0)));
		Point2D g3 = gradients.get(inMapSpace(p.getAddition(0, 1)));
		Point2D g4 = gradients.get(inMapSpace(p.getAddition(1, 1)));
		
		Point2D sub1 = pInMapSpace.getSubtraction(sw);
		Point2D sub2 = pInMapSpace.getSubtraction(se);
		Point2D sub3 = pInMapSpace.getSubtraction(nw);
		Point2D sub4 = pInMapSpace.getSubtraction(ne);
		
		double s = g1.getDotProduct(sub1);
		double t = g2.getDotProduct(sub2);
		double u = g3.getDotProduct(sub3);
		double v = g4.getDotProduct(sub4);
		
		double Sx = ease(pInMapSpace.x);
		double a = s+Sx*(t-s);
		double b = u+Sx*(v-u);
		
		double Sy = ease(pInMapSpace.y);
		double res = a+Sy*(b-a);
		return (res+1)/2;
	}
	
	private double ease(double val){
		// s-curve or ease curve : 3p^2-2p^3
		return 3*Math.pow(val-(int)val, 2) - 2*Math.pow(val-(int)val, 3);
	}
	
	private Point2D inMapSpace(Point2D p){
		double x = p.x%RESOLUTION;
		if(x < 0)
			x += RESOLUTION;
		double y = p.y%RESOLUTION;
		if(y < 0)
			y += RESOLUTION;
		return new Point2D(x, y);
		
	}
}
