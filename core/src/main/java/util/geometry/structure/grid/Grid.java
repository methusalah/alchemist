package util.geometry.structure.grid;

import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.ir.GetSplitState;
import model.world.terrain.heightmap.Height;
import util.geometry.collections.Map2D;
import util.geometry.geom2d.Point2D;

public class Grid<T extends Node> extends Map2D<T> {

	protected final Point2D coord;

	public Grid(){
		super();
		coord = Point2D.ORIGIN;
	}
	public Grid(int width, int height) {
		super(width, height);
		coord = Point2D.ORIGIN;
	}
	public Grid(int width, int height, Point2D coord) {
		super(width, height);
		this.coord = coord;
	}
	
	public T getNorthNode(T n){
		int nIndex = n.index+xSize;
		return nIndex < xSize*ySize? get(nIndex): null;
	}

	public T getSouthNode(T n){
		int sIndex = n.index-xSize;
		return sIndex >= 0? get(sIndex): null;
	}

	public T getEastNode(T n){
		return n.index % xSize == xSize-1? null: get(n.index+1); 
	}

	public T getWestNode(T n){
		return n.index % xSize == 0? null: get(n.index-1); 
	}
	
	public List<T> getInSquareWithoutCenter(T n, int distance) {
		List<T> res = new ArrayList<>();
		for (int x = -distance; x <= distance; x++) {
			for (int y = -distance; y <= distance; y++) {
				if (x == 0 && y == 0) {
					continue;
				}
				Point2D p = getCoord(n.index).getAddition(x, y);
				if(!isInBounds(p))
					continue;
				res.add((T)get(p));
			}
		}
		return res;
	}
	public List<T> getInSquare(T n, int distance) {
		List<T> res = getInSquareWithoutCenter(n, distance);
		res.add(n);
		return res;
	}

	public List<T> get8Around(T n) {
		List<T> res = getInSquareWithoutCenter(n, 1);
		return res;
	}
	
	public List<T> get9Around(T n) {
		List<T> res = getInSquare(n, 1);
		return res;
	}

	public List<T> get25Around(T n) {
		List<T> res = getInSquare(n, 2);
		return res;
	}

	public List<T> get4Around(T node) {
		T n = getNorthNode(node);
		T s = getSouthNode(node);
		T e = getEastNode(node);
		T w = getWestNode(node);
		List<T> res = new ArrayList<>();
		if (n != null) {
			res.add(n);
		}
		if (s != null) {
			res.add(s);
		}
		if (e != null) {
			res.add(e);
		}
		if (w != null) {
			res.add(w);
		}
		return res;
	}

	public List<T> getInCircle(Point2D p, double distance) {
		int ceiled = (int)Math.ceil(distance);
		List<T> res = new ArrayList<>();
		for(T node : getInSquare(get(p), ceiled)){
			Point2D nodeCenter = getCoord(node.getIndex()).getAddition(0.5);
			if(nodeCenter.getDistance(p) < distance)
				res.add(node);
		}
		return res;
	}
	public Point2D getCoord() {
		return coord;
	}
	
	@Override
	public T get(int x, int y) {
		return super.get(x-(int)coord.x, y-(int)coord.y);
	}
	
	@Override
	public Point2D getCoord(int index) {
		return super.getCoord(index).getAddition(coord);
	}
	
	@Override
	public boolean isInBounds(Point2D p) {
		return super.isInBounds(p.getSubtraction(coord));
	}
//	
//	@Override
//	public boolean isInBounds(int x, int y) {
//    	return x >= coord.x && x < coord.x+xSize && y >= coord.y && y < coord.y+ySize;
//    }

	@Override
	protected void checkInBounds(int x, int y){
    	if(!isInBounds(x, y))
    		throw new IllegalArgumentException("("+x+";"+y+") is out of bounds (x-size = "+xSize+"; y-size = "+ySize + " (grid coord : "+coord);
    }

}
