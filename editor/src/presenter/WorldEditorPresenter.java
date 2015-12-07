package presenter;

import model.world.HeightMapTool;
import model.world.PopulationTool;
import model.world.Tool;
import model.world.WorldData;
import model.world.atlas.AtlasTool;
import util.LogUtil;
import util.event.EventManager;
import application.topDownScene.state.WorldToolState;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;

public class WorldEditorPresenter {
	private final JmeForImageView jme;
	private final WorldData worldData;
	
	private final HeightMapTool heightmapTool;
	private final AtlasTool atlasTool;
	private final PopulationTool populationTool;

	public WorldEditorPresenter(JmeForImageView jme, WorldData worldData) {
		this.jme = jme;
		this.worldData = worldData;
		EventManager.register(this);
		
		heightmapTool = new HeightMapTool(worldData);
		atlasTool = new AtlasTool(worldData);
		populationTool = new PopulationTool(worldData);
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
		jme.enqueue(app -> setTool(app, tool));
	}
	
	public void saveWorld(){
		worldData.saveDrawnRegions();
	}
	
	static private boolean setTool(SimpleApplication app, Tool t) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(WorldToolState.class).setTool(t);
		
		return true;
	}

}
