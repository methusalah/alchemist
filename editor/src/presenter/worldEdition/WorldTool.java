package presenter.worldEdition;

import model.world.WorldData;
import presenter.EditorPlatform;
import util.geometry.geom2d.Point2D;

public abstract class WorldTool extends Tool {
	protected Point2D coord = Point2D.ORIGIN;
	protected final WorldData world = EditorPlatform.getWorldData();
	
	public void setCoord(Point2D coordInScene){
		coord = coordInScene;
	}
}
