package model.world;

import util.geometry.geom2d.Point2D;

public abstract class Tool {
	protected Point2D coord = Point2D.ORIGIN; 
	protected final WorldData world;
	
	public Tool(WorldData world) {
		this.world = world;
	}

	public void onSingleAction(){};
	public void onActionStart(){};
	public void onActionEnd(){};
	public void setCoord(Point2D coordInScene){
		coord = coordInScene;
	}
}
