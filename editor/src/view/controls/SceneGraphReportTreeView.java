package view.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.scene.Spatial;

import controller.SceneGraphReportNode;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import util.LogUtil;

public class SceneGraphReportTreeView extends TreeView<SceneGraphReportNode>{

	private Map<Spatial, Boolean> expandMap = new HashMap<>();
	private List<Spatial> selection = new ArrayList<>();
	
	public SceneGraphReportTreeView(ObjectProperty<SceneGraphReportNode> rootNodeProperty) {
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		//setPrefHeight(2000);
		setShowRoot(false);
		
		TreeItem<SceneGraphReportNode> root = new TreeItem<>();
		root.valueProperty().bind(rootNodeProperty);
		root.valueProperty().addListener((observable, oldValue, newValue) -> {
			root.getChildren().clear();
			//this.getSelectionModel().clearSelection();
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
