package model.world;

import util.geometry.geom2d.Point2D;

public abstract class Tool {
	
	protected Point2D coord = Point2D.ORIGIN; 
	
	public void onSingleAction(){};
	public void onActionStart(){};
	public void onActionEnd(){};
	public void setCoord(Point2D coordInScene){
		coord = coordInScene;
	};

}
