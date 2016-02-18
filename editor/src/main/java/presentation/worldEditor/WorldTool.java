package main.java.presentation.worldEditor;

import util.geometry.geom2d.Point2D;

public abstract class WorldTool extends Tool {
	protected Point2D coord = Point2D.ORIGIN;
	
	public void setCoord(Point2D coordInScene){
		coord = coordInScene;
	}
}
