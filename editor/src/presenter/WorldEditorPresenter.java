package presenter;

import com.jme3.scene.Node;

import app.AppFacade;
import controller.SpatialSelector;
import presenter.worldEdition.HeightMapToolPresenter;
import presenter.worldEdition.PopulationTool;
import presenter.worldEdition.Tool;
import presenter.worldEdition.WorldTool;
import presenter.worldEdition.atlas.AtlasToolPresenter;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.WorldEditorTab;

public class WorldEditorPresenter {
	private final HeightMapToolPresenter heightmapTool;
	private final AtlasToolPresenter atlasTool;
	private final PopulationTool populationTool;

	private WorldTool selectedTool = null;
	
	public WorldEditorPresenter(WorldEditorTab view) {
		heightmapTool = new HeightMapToolPresenter(EditorPlatform.getWorldData());
		atlasTool = new AtlasToolPresenter(EditorPlatform.getWorldData());
		populationTool = new PopulationTool(EditorPlatform.getWorldData());
	}

	public HeightMapToolPresenter getHeightmapTool() {
		return heightmapTool;
	}

	public AtlasToolPresenter getAtlasTool() {
		return atlasTool;
	}

	public PopulationTool getPopulationTool() {
		return populationTool;
	}
	
	public void selectTool(WorldTool tool){
		LogUtil.info("select tool " + tool);

		selectedTool = tool;
		EditorPlatform.getSelectionProperty().setValue(null);
	}
	
	public void saveWorld(){
		EditorPlatform.getWorldData().saveDrawnRegions();
	}
	
	public void handleTabOpened(){
		
	}
	
	public void doPrimaryAction(){
		selectedTool.doPrimary();
	}

	public void doSecondaryAction(){
		selectedTool.doSecondary();
	}
	
	public void setNewMousePosition(Point2D screenCoord){
		Node worldNode = (Node)AppFacade.getMainSceneNode().getChild("World");
		selectedTool.setCoord(SpatialSelector.getCoord(worldNode, screenCoord));
	}
}
