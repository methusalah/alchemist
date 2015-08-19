package view;

import util.annotation.AssetManagerRef;
import util.annotation.GuiNodeRef;
import util.annotation.RootNodeRef;
import util.annotation.ViewPortRef;
import view.acting.ActorDrawer;
import view.mapDrawing.LightDrawer;
import view.mapDrawing.MapDrawer;
import view.material.MaterialManager;

import com.google.inject.Inject;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class View {
	@RootNodeRef
	protected Node rootNode;
	@GuiNodeRef
	protected Node guiNode;
	@ViewPortRef
	protected ViewPort viewPort;
	@AssetManagerRef
	protected AssetManager assetManager;
	
	// Drawers
	protected LightDrawer lightDrawer;

	@Inject
	public View() {
		createSky();
	}

	private void createSky() {
		viewPort.setBackgroundColor(new ColorRGBA(135f / 255f, 206f / 255f, 250f / 255f, 1));
		Geometry xAxe = new Geometry();
		xAxe.setMesh(new Box(5, 0.1f, 0.1f));
		xAxe.setMaterial(MaterialManager.getColor(ColorRGBA.Brown));
		xAxe.setLocalTranslation(5, 0, 0);
		rootNode.attachChild(xAxe);

		Geometry zAxe = new Geometry();
		zAxe.setMesh(new Box(0.1f, 0.1f, 5));
		zAxe.setMaterial(MaterialManager.greenMaterial);
		zAxe.setLocalTranslation(0, 0, 5);
		rootNode.attachChild(zAxe);

		Geometry yAxe = new Geometry();
		yAxe.setMesh(new Box(0.1f, 5, 0.1f));
		yAxe.setMaterial(MaterialManager.redMaterial);
		yAxe.setLocalTranslation(0, 5, 0);
		rootNode.attachChild(yAxe);
	}
}
