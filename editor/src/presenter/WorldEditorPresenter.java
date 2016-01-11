package presenter;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

import model.state.WorldToolState;
import model.world.HeightMapTool;
import model.world.PopulationTool;
import model.world.Tool;
import model.world.atlas.AtlasTool;
import presenter.scene.EditionInputListener;
import util.event.EventManager;

public class WorldEditorPresenter {
	private final HeightMapTool heightmapTool;
	private final AtlasTool atlasTool;
	private final PopulationTool populationTool;

	private final EditionInputListener edition;

	
	public WorldEditorPresenter() {
		heightmapTool = new HeightMapTool(EditorPlatform.getWorldData());
		atlasTool = new AtlasTool(EditorPlatform.getWorldData());
		populationTool = new PopulationTool(EditorPlatform.getWorldData());
		edition = new EditionInputListener(EditorPlatform.getScene());
	}

	public HeightMapTool getHeightmapTool() {
		return heightmapTool;
	}

	public AtlasTool getAtlasTool() {
		return atlasTool;
	}

	public PopulationTool getPopulationTool() {
		return populationTool;
	}
	
	public void selectTool(Tool tool){
		EditorPlatform.getScene().enqueue(app -> setTool(app, tool));
		EditorPlatform.getSelectionProperty().setValue(null);
		EditorPlatform.getSceneInputManager().setConcurrentListener(edition);
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

}
