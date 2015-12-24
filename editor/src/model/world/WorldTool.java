package model.world;

import util.geometry.geom2d.Point2D;

public abstract class WorldTool extends Tool {
	protected Point2D coord = Point2D.ORIGIN; 
	protected final WorldData world;
	
	protected Runnable currentWork;
	
	public WorldTool(WorldData world) {
		this.world = world;
	}

	public void setCoord(Point2D coordInScene){
		coord = coordInScene;
	};
	
	@Override
	public void onUpdate(float elapsedTime) {
		coord = selector.getPointedCoordInPlan();
		if(currentWork != null)
			currentWork.run();
	}
}
