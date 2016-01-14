package view.controls.jmeScene;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Iterables;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

import util.geometry.geom3d.Point3D;
import view.material.MaterialManager;
import view.math.TranslateUtil;

public class InstrumentPart{
	private final Map<String, Geometry> viewGeometries = new HashMap<>();
	private final Map<String, Geometry> gripGeometries = new HashMap<>();
	private final Node viewNode, gripNode;
	private final String name;
	
	public InstrumentPart(String partName) {
		this.name = partName;
		viewNode = new Node(name + " view");
		gripNode = new Node(name + " grip");
	}
	
	public void setHover(boolean value){
		
		for(Geometry g : viewGeometries.values()){
			if((boolean)g.getUserData("lighted"))
				if(value)
					g.getMaterial().setColor("GlowColor", (ColorRGBA)(g.getMaterial().getParam("Color").getValue()));
				else
					g.getMaterial().clearParam("GlowColor");
		}
	}
	
	public void putViewAndGripGeometry(String name, ColorRGBA color, Mesh mesh, boolean lightedOnHover){
		putViewGeometry(name, color, mesh, lightedOnHover);
		putGripGeometry(name, mesh);
	}
	
	public void putViewGeometry(String viewName, ColorRGBA color, Mesh mesh, boolean lightedOnHover){
		Geometry g = new Geometry(viewName);
		g.setMaterial(MaterialManager.getColor(color));
		g.getMaterial().getAdditionalRenderState().setDepthTest(false);
		g.setMesh(mesh);
		g.setUserData("lighted", lightedOnHover);
		viewGeometries.put(viewName, g);
		viewNode.attachChild(g);
	}

	public void putGripGeometry(String gripName, Mesh mesh){
		Geometry g = new Geometry(gripName);
		g.setMaterial(MaterialManager.blackMaterial);
		g.setMesh(mesh);
		gripGeometries.put(gripName, g);
		gripNode.attachChild(g);
	}
	
	public void setScale(double scale){
		for(Geometry g : Iterables.concat(viewGeometries.values(), gripGeometries.values())){
			g.setLocalScale((float)scale);
		}
	}
	
	public void setRotation(Quaternion q){
		for(Geometry g : Iterables.concat(viewGeometries.values(), gripGeometries.values())){
			g.setLocalRotation(q);
		}
	}

	public void setTranslation(Point3D p){
		for(Geometry g : Iterables.concat(viewGeometries.values(), gripGeometries.values())){
			g.setLocalTranslation(TranslateUtil.toVector3f(p));
		}
	}
	
	public boolean containsGrip(Geometry g){
		return gripGeometries.containsValue(g);
	}

	public Node getViewNode() {
		return viewNode;
	}

	public Node getGripNode() {
		return gripNode;
	}
	
	Function<Object, Object> dragFunction = null;
	public void setOnDrag(Function<Object, Object> f){
		dragFunction = f;
	}
	
	public void drag(){
		if(dragFunction != null)
			dragFunction.apply(null);
	}
	
}
