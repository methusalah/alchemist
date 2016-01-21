package presenter.worldEdition;

import model.world.WorldData;
import util.geometry.geom2d.Point2D;

public abstract class WorldTool extends Tool {
	protected Point2D coord = Point2D.ORIGIN;
	protected final WorldData world;
	
	public WorldTool(WorldData world) {
		this.world = world;
	}

	public void setCoord(Point2D coordInScene){
		coord = coordInScene;
	}
}
