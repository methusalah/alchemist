package model.world.terrain.atlas;

import java.util.ArrayList;

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

	Terrain terrain;

	public AtlasExplorer(Terrain terrain){
		this.terrain = terrain;
	}

	public Point2D getInMapSpace(Point2D p){
		return p.getMult(terrain.getWidth(), terrain.getHeight()).getDivision(terrain.getAtlas().getWidth(), terrain.getAtlas().getHeight());
	}

	public Point2D getInAtlasSpace(Point2D p){
		return p.getMult(terrain.getAtlas().getWidth(), terrain.getAtlas().getHeight()).getDivision(terrain.getWidth(), terrain.getHeight());
	}

	public double getInAtlasSpace(double distance){
		return distance * terrain.getAtlas().getWidth() / terrain.getWidth();
	}

	public double getInMapSpace(double distance){
		return distance * terrain.getWidth() / terrain.getAtlas().getWidth();
	}

	public ArrayList<Point2D> getPixelsInMapSpaceSquare(Point2D center, double radius){
		center = getInAtlasSpace(center);
		radius = getInAtlasSpace(radius);
		ArrayList<Point2D> res = new ArrayList<>();
		int minX = (int)Math.round(Math.max(center.x-radius, 0));
		int maxX = (int) Math.round(Math.min(center.x + radius, terrain.getAtlas().getWidth() - 1));
		int minY = (int)Math.round(Math.max(center.y-radius, 0));
		int maxY = (int) Math.round(Math.min(center.y + radius, terrain.getAtlas().getHeight() - 1));
		for(int x=minX; x<maxX; x++) {
			for(int y=minY; y<maxY; y++){
				Point2D p = new Point2D(x, y);
				res.add(p);
			}
		}
		return res;
	}

	public ArrayList<Point2D> getPixelsInMapSpaceCircle(Point2D center, double radius){
		ArrayList<Point2D> res = new ArrayList<>();
		for(Point2D p : getPixelsInMapSpaceSquare(center, radius)) {
			if(p.getDistance(getInAtlasSpace(center)) < getInAtlasSpace(radius)) {
				res.add(p);
			}
		}
		return res;
	}

	public ArrayList<Point2D> getPixelsInMapSpaceDiamond(Point2D center, double radius){
		radius *= 1.414;
		ArrayList<Point2D> res = new ArrayList<>();
		for(Point2D p : getPixelsInMapSpaceSquare(center, radius)) {
			if(p.getManathanDistance(getInAtlasSpace(center)) < getInAtlasSpace(radius)) {
				res.add(p);
			}
		}
		return res;
	}
}
