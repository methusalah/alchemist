package presenter;

import com.jme3.scene.Node;

import app.AppFacade;
import controller.SpatialSelector;
import model.ES.processor.world.WorldProc;
import presenter.worldEdition.HeightMapToolPresenter;
import presenter.worldEdition.PopulationToolPresenter;
import presenter.worldEdition.Tool;
import presenter.worldEdition.WorldTool;
import presenter.worldEdition.atlas.AtlasToolPresenter;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.WorldEditorTab;

public class WorldEditorPresenter {
	private WorldTool selectedTool = null;
	
	public WorldEditorPresenter(WorldEditorTab view) {
	}

	public void selectTool(WorldTool tool){
		LogUtil.info("select tool " + tool);

		selectedTool = tool;
		EditorPlatform.getSelectionProperty().setValue(null);
	}
	
	public void saveWorld(){
		AppFacade.getStateManager().getState(WorldProc.class).saveDrawnRegions();
	}
	
	public void handleTabOpened(){
		
	}
	
	public void beginAction(){
		selectedTool.begin();
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
