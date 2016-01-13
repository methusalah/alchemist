package view.controls.jmeScene;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

import view.material.MaterialManager;

public class SceneTool{
	private final Geometry view, grip;
	private final ColorRGBA color;
	
	public SceneTool(String name, ColorRGBA color) {
		this.color = color;
		view = new Geometry(name + " view");
		grip = new Geometry(name + " grip");
		view.setMaterial(MaterialManager.getColor(color));
		view.getMaterial().getAdditionalRenderState().setDepthTest(false);
		grip.setMaterial(MaterialManager.blackMaterial);
	}
	
	public void setMesh(Mesh mesh){
		view.setMesh(mesh);
		grip.setMesh(mesh);
	}

	public void setMesh(Mesh viewMesh, Mesh gripMesh){
		view.setMesh(viewMesh);
		grip.setMesh(gripMesh);
	}
	
	public void setHover(boolean value){
		if(value)
			view.getMaterial().setColor("GlowColor", color);
		else
			view.getMaterial().clearParam("GlowColor");
	}

	public Geometry getView() {
		return view;
	}

	public Geometry getGrip() {
		return grip;
	}
}
