package main.java.model.tempImport;

import java.util.List;

import com.jme3.scene.Spatial;

public class SceneGraphReportNode {
	private final Spatial spatial;
	private final List<SceneGraphReportNode> children;
	
	public SceneGraphReportNode(Spatial spatial, List<SceneGraphReportNode> children) {
		this.spatial = spatial;
		this.children = children;
	}

	public List<SceneGraphReportNode> getChildren() {
		return children;
	}
	
	@Override
	public String toString() {
		return spatial.getName() == null || spatial.getName().isEmpty()? "Unnamed spatial" : spatial.getName();
	}

	public Spatial getSpatial() {
		return spatial;
	}
}
