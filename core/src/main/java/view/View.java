package view;

import util.LogUtil;
import util.event.EventManager;
import util.event.MapResetEvent;
import view.material.MaterialManager;
import app.AppFacade;

import com.google.common.eventbus.Subscribe;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class View {
	private Node plane = new Node("planeNode");
	
	public View() {
		createSky();
		EventManager.register(this);
	}

	private void createSky() {
		AppFacade.getViewPort().setBackgroundColor(new ColorRGBA(135f / 255f, 206f / 255f, 250f / 255f, 1));
		Geometry xAxe = new Geometry("xAxe");
		xAxe.setMesh(new Box(5, 0.1f, 0.1f));
		xAxe.setMaterial(MaterialManager.getColor(ColorRGBA.Brown));
		xAxe.setLocalTranslation(5, 0, 0);
		AppFacade.getRootNode().attachChild(xAxe);

		Geometry zAxe = new Geometry("zAxe");
		zAxe.setMesh(new Box(0.1f, 0.1f, 5));
		zAxe.setMaterial(MaterialManager.greenMaterial);
		zAxe.setLocalTranslation(0, 0, 5);
		AppFacade.getRootNode().attachChild(zAxe);

		Geometry yAxe = new Geometry("yAxe");
		yAxe.setMesh(new Box(0.1f, 5, 0.1f));
		yAxe.setMaterial(MaterialManager.redMaterial);
		yAxe.setLocalTranslation(0, 5, 0);
		AppFacade.getRootNode().attachChild(yAxe);
		
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
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		nodeToString(sb, AppFacade.getRootNode(), 0);
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
}
