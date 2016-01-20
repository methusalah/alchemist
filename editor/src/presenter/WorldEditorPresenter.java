package presenter;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import model.state.WorldToolState;
import model.world.HeightMapToolPresenter;
import model.world.PopulationTool;
import model.world.Tool;
import model.world.atlas.AtlasTool;
import presenter.common.WorldEditorInputListener;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import view.HierarchyTab;
import view.WorldEditorTab;

public class WorldEditorPresenter {
	private final HeightMapToolPresenter heightmapTool;
	private final AtlasTool atlasTool;
	private final PopulationTool populationTool;

	private Tool selectedTool = null;
	
	public WorldEditorPresenter(WorldEditorTab view) {
		heightmapTool = new HeightMapToolPresenter(EditorPlatform.getWorldData());
		atlasTool = new AtlasTool(EditorPlatform.getWorldData());
		populationTool = new PopulationTool(EditorPlatform.getWorldData());
	}

	public HeightMapToolPresenter getHeightmapTool() {
		return heightmapTool;
	}

	public AtlasTool getAtlasTool() {
		return atlasTool;
	}

	public PopulationTool getPopulationTool() {
		return populationTool;
	}
	
	public void selectTool(Tool tool){
		selectedTool = tool;
		EditorPlatform.getScene().enqueue(app -> setTool(app, tool));
		EditorPlatform.getSelectionProperty().setValue(null);
	}
	
	public void saveWorld(){
		EditorPlatform.getWorldData().saveDrawnRegions();
	}
	
	static private boolean setTool(SimpleApplication app, Tool t) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(WorldToolState.class).setTool(t);
		return true;
	}
	
	public void handleTabOpened(){
		
	}
	
	public void doPrimaryActionAt(Point2D screenCoord){
		selectedTool.doPrimary();
	}

	public void doSecondaryActionAt(Point2D screenCoord){
		selectedTool.doSecondary();
	}
}
