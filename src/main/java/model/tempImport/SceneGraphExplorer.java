package model.tempImport;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SceneGraphExplorer extends AbstractAppState {

	public SceneGraphReportNode getReport(){
		return reportSceneDeeply(RendererPlatform.getApp().getRootNode());
	}

	private SceneGraphReportNode reportSceneDeeply(Spatial s){
		List<SceneGraphReportNode> children = new ArrayList<>();
		if(s instanceof Node){
			for(Spatial child : ((Node)s).getChildren())
				children.add(reportSceneDeeply(child));
		}
		return new SceneGraphReportNode(s, children);
	}

}
