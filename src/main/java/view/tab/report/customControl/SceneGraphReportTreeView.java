package view.tab.report.customControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.scene.Spatial;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.tempImport.SceneGraphReportNode;

public class SceneGraphReportTreeView extends TreeView<SceneGraphReportNode>{

	private Map<Spatial, Boolean> expandMap = new HashMap<>();
	private List<Spatial> selection = new ArrayList<>();
	
	public SceneGraphReportTreeView(ObjectProperty<SceneGraphReportNode> rootNodeProperty) {
		setShowRoot(false);
		
		TreeItem<SceneGraphReportNode> root = new TreeItem<>();
		root.valueProperty().bind(rootNodeProperty);
		root.valueProperty().addListener((observable, oldValue, newValue) -> {
			List<Spatial> selectionSave = new ArrayList<>(selection);
			this.getSelectionModel().clearSelection();
			selection = selectionSave;
			root.getChildren().clear();
			addItem(root, newValue);
		});
		
		getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null && !selection.contains(newValue.getValue().getSpatial())){
				selection.add(newValue.getValue().getSpatial());
			}
			if(oldValue != null && selection.contains(oldValue.getValue().getSpatial())){
				selection.remove(oldValue.getValue().getSpatial());
			}
		});
		setRoot(root);
	}
	
	private void addItem(TreeItem<SceneGraphReportNode> parent, SceneGraphReportNode ep){
		Spatial s = ep.getSpatial();
		TreeItem<SceneGraphReportNode> i = new TreeItem<>(ep);
		if(expandMap.containsKey(s))
				i.setExpanded(expandMap.get(s));
		i.expandedProperty().addListener((observable, oldValue, newValue) -> expandMap.put(s, newValue));
		
		parent.getChildren().add(i);
		if(selection.contains(s))
			getSelectionModel().select(i);
		for(SceneGraphReportNode childNode : ep.getChildren()){
			addItem(i, childNode);
		}
	}

	
}
