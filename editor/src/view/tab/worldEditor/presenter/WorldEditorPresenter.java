package view.tab.worldEditor.presenter;

import com.jme3.scene.Node;

import app.AppFacade;
import controller.SpatialSelector;
import model.EditorPlatform;
import model.ES.processor.world.WorldProc;
import util.geometry.geom2d.Point2D;

public class WorldEditorPresenter {
	private WorldTool selectedTool = null;
	
	public void selectTool(WorldTool tool){
		selectedTool = tool;
		EditorPlatform.getSelectionProperty().setValue(null);
	}
	
	public void saveWorld(){
		AppFacade.getStateManager().getState(WorldProc.class).saveDrawnRegions();
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
