package model.world;

import util.geometry.geom2d.Point2D;

public abstract class Tool {
	protected Point2D coord = Point2D.ORIGIN; 
	protected final WorldData world;
	
	protected Runnable currentWork;
	
	public Tool(WorldData world) {
		this.world = world;
	}

	public void onPrimarySingleAction(){};
	public void onPrimaryActionStart(){};
	public void onPrimaryActionEnd(){};

	public void onSecondarySingleAction(){};
	public void onSecondaryActionStart(){};
	public void onSecondaryActionEnd(){};
	public void setCoord(Point2D coordInScene){
		coord = coordInScene;
	}
	
	public void onUpdate(float elapsedTime){
		if(currentWork != null)
			currentWork.run();
	};
}
