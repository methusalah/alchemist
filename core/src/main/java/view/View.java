package view;

import util.LogUtil;
import util.annotation.GuiNodeRef;
import util.annotation.RootNodeRef;
import util.annotation.ViewPortRef;
import util.event.EventManager;
import util.event.MapResetEvent;
import view.mapDrawing.LightDrawer;
import view.mapDrawing.MapDrawer;
import view.material.MaterialManager;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class View {
	protected final Node rootNode;
	protected final Node guiNode;
	protected final ViewPort viewPort;
	protected final AssetManager assetManager;
	protected final LightDrawer lightDrawer;
	protected final MapDrawer mapDrawer;
	
	private Node plane = new Node("planeNode");
	
	@Inject
	public View(@RootNodeRef Node rootNode, @GuiNodeRef Node guiNode, @ViewPortRef ViewPort viewPort, AssetManager assetManager, Injector injector) {
		this.rootNode = rootNode;
		this.viewPort = viewPort;
		this.guiNode = guiNode;
		mapDrawer = injector.getInstance(MapDrawer.class);
		lightDrawer = injector.getInstance(LightDrawer.class);
		this.assetManager = assetManager;
		createSky();
		rootNode.attachChild(mapDrawer.mainNode);
		EventManager.register(this);
	}

	private void createSky() {
		viewPort.setBackgroundColor(new ColorRGBA(135f / 255f, 206f / 255f, 250f / 255f, 1));
		Geometry xAxe = new Geometry("xAxe");
		xAxe.setMesh(new Box(5, 0.1f, 0.1f));
		xAxe.setMaterial(MaterialManager.getColor(ColorRGBA.Brown));
		xAxe.setLocalTranslation(5, 0, 0);
		rootNode.attachChild(xAxe);

		Geometry zAxe = new Geometry("zAxe");
		zAxe.setMesh(new Box(0.1f, 0.1f, 5));
		zAxe.setMaterial(MaterialManager.greenMaterial);
		zAxe.setLocalTranslation(0, 0, 5);
		rootNode.attachChild(zAxe);

		Geometry yAxe = new Geometry("yAxe");
		yAxe.setMesh(new Box(0.1f, 5, 0.1f));
		yAxe.setMaterial(MaterialManager.redMaterial);
		yAxe.setLocalTranslation(0, 5, 0);
		rootNode.attachChild(yAxe);
		
		Geometry planeGeom = new Geometry("plane");
		planeGeom.setMesh(new Box(500, 500, 0.01f));
		planeGeom.setMaterial(MaterialManager.cyanMaterial);
		plane.attachChild(planeGeom);
	}
	
	public Node getPlane(){
		return plane;
	}
	
	public void update(float elapsedTime){
		
	}

	@Subscribe
	public void firstRender(MapResetEvent e){
		lightDrawer.Initialize();
		mapDrawer.renderTiles();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		nodeToString(sb, rootNode, 0);
		return sb.toString();
	}
	public void nodeToString(StringBuilder sb, Spatial s, int indent){
		for(int i = 0; i<indent; i++)
			sb.append("    ");
		sb.append(s.toString() + " pos : " + s.getLocalTranslation());
		LogUtil.info(sb.toString());
		if(s instanceof Node)
			for(Spatial child : ((Node)s).getChildren())
				nodeToString(sb, child, indent+1);
	}
	
	public Node getRootNode(){
		return rootNode;
	}
}
