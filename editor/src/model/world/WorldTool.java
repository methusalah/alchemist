package model.world;

import com.jme3.scene.Node;

import app.AppFacade;
import controller.SpatialSelector;
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
	}
	
	@Override
	public void onUpdate(float elapsedTime) {
		Node worldNode = (Node)AppFacade.getMainSceneNode().getChild("World");
		//coord = selector.getPointedCoordInPlan();
		coord = SpatialSelector.getCoord(worldNode, selector.getCoordInScreenSpace());
		if(currentWork != null)
			currentWork.run();
	}
}
