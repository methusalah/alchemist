package main.java.presentation.worldEditor.atlas;

import java.util.ArrayList;
import java.util.List;

import main.java.model.world.terrain.atlas.Atlas;
import util.geometry.geom2d.Point2D;

/**
 * Translate coordinates between map's and atlas' spaces.
 *
 * Also finds groups of pixel of a certain shape.
 *
 * @author Benoît
 */
public class AtlasExplorer {

	public static Point2D getInAtlasScale(Point2D p){
		return p.getMult(Atlas.RESOLUTION_RATIO);
	}

	public static Point2D getInWorldScale(Point2D p){
		return p.getDivision(Atlas.RESOLUTION_RATIO);
	}

	public static double getInAtlasScale(double distance){
		return distance * Atlas.RESOLUTION_RATIO;
	}

	public static double getInTerrainScale(double distance){
		return distance / Atlas.RESOLUTION_RATIO;
	}

	public static List<Pixel> getPixelsInSquare(Point2D centerInWorldScale, double radiusInWorldScale){
		Point2D center = getInAtlasScale(centerInWorldScale);
		double radius = getInAtlasScale(radiusInWorldScale);
		List<Pixel> res = new ArrayList<>();
		int minX = (int)Math.round(center.x - radius);
		int maxX = (int)Math.round(center.x + radius);
		int minY = (int)Math.round(center.y - radius);
		int maxY = (int)Math.round(center.y + radius);
		for(int x = minX; x < maxX; x++) {
			for(int y = minY; y < maxY; y++){
				Pixel pixel = new Pixel();
				pixel.coord = new Point2D(x, y);
				pixel.worldCoord = getInWorldScale(pixel.coord);
				res.add(pixel);
			}
		}
		return res;
	}

	public static List<Pixel> getPixelsInCircle(Point2D center, double radius){
		List<Pixel> res = new ArrayList<>();
		for(Pixel p : getPixelsInSquare(center, radius)) {
			if(p.worldCoord.getDistance(center) < radius) {
				res.add(p);
			}
		}
		return res;
	}

	public static List<Pixel> getPixelsInDiamond(Point2D center, double radius){
		radius *= 1.414;
		List<Pixel> res = new ArrayList<>();
		for(Pixel p : getPixelsInSquare(center, radius)) {
			if(p.worldCoord.getManathanDistance(center) < radius) {
				res.add(p);
			}
		}
		return res;
	}
}
