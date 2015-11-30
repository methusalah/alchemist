package model.world.terrain.atlas;

import java.util.ArrayList;
import java.util.List;

import model.world.Region;
import model.world.terrain.Terrain;
import util.geometry.geom2d.Point2D;

/**
 * Translate coordinates between map's and atlas' spaces.
 *
 * Also finds groups of pixel of a certain shape.
 *
 * @author Benoît
 */
public class AtlasExplorer {

	public static Point2D getInTerrainSpace(Terrain t, Point2D p){
		return p.getDivision(Atlas.RESOLUTION_RATIO).getAddition(t.getHeightMap().getCoord());
	}

	public static Point2D getInAtlasSpace(Terrain t, Point2D p){
		return p.getSubtraction(t.getHeightMap().getCoord()).getMult(Atlas.RESOLUTION_RATIO);
	}

	public static double getInAtlasSpace(double distance){
		return distance * Atlas.RESOLUTION_RATIO;
	}

	public static double getInTerrainSpace(double distance){
		return distance / Atlas.RESOLUTION_RATIO;
	}

	public static List<Point2D> getPixelsInAtlasSpaceSquare(Terrain t, Point2D center, double radius){
		center = getInAtlasSpace(t, center);
		radius = getInAtlasSpace(radius);
		List<Point2D> res = new ArrayList<>();
		int minX = (int)Math.round(center.x - radius);
		int maxX = (int)Math.round(center.x + radius);
		int minY = (int)Math.round(center.y - radius);
		int maxY = (int)Math.round(center.y + radius);
		for(int x=minX; x<maxX; x++) {
			for(int y=minY; y<maxY; y++){
				Point2D p = new Point2D(x, y);
				res.add(p);
			}
		}
		return res;
	}

	public static List<Point2D> getPixelsInAtlasSpaceCircle(Terrain t, Point2D center, double radius){
		List<Point2D> res = new ArrayList<>();
		for(Point2D p : getPixelsInAtlasSpaceSquare(t, center, radius)) {
			if(p.getDistance(getInAtlasSpace(t, center)) < getInAtlasSpace(radius)) {
				res.add(p);
			}
		}
		return res;
	}

	public static List<Point2D> getPixelsInAtlasSpaceDiamond(Terrain t, Point2D center, double radius){
		radius *= 1.414;
		List<Point2D> res = new ArrayList<>();
		for(Point2D p : getPixelsInAtlasSpaceSquare(t, center, radius)) {
			if(p.getManathanDistance(getInAtlasSpace(t, center)) < getInAtlasSpace(radius)) {
				res.add(p);
			}
		}
		return res;
	}
}
