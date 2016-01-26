package model.world;

import util.geometry.geom2d.Point2D;

public class RegionId {

	private final String id;
	private final Point2D offset;
	
	public RegionId(Point2D coord) {
		int x = (int)Math.floor(coord.x/Region.RESOLUTION);
		int y = (int)Math.floor(coord.y/Region.RESOLUTION);
		this.offset = new Point2D(x*Region.RESOLUTION, y*Region.RESOLUTION);
		id = x+","+y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RegionId){
			if(((RegionId)obj).id.equals(id))
				return true;
		}
		return super.equals(obj);
	}

	public String getId() {
		return id;
	}

	public Point2D getOffset() {
		return offset;
	}
	
	@Override
	public String toString() {
		return id + " " + offset; 
	}
	
	
	
}
