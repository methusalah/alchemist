package plugin.infiniteWorld.editor.presentation;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.state.SpatialSelector;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.jme3.scene.Node;

import logic.processor.logic.world.WorldProc;
import util.geometry.geom2d.Point2D;

public class WorldEditorPresenter {
	private WorldTool selectedTool = null;
	
	public void selectTool(WorldTool tool){
		selectedTool = tool;
		EditorPlatform.getSelectionProperty().setValue(null);
	}
	
	public void saveWorld(){
		RendererPlatform.getStateManager().getState(WorldProc.class).saveDrawnRegions();
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
		Node worldNode = (Node)RendererPlatform.getMainSceneNode().getChild("World");
		selectedTool.setCoord(SpatialSelector.getCoord(worldNode, screenCoord));
	}
}
