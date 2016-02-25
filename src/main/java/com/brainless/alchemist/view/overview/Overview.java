package com.brainless.alchemist.view.overview;

import com.brainless.alchemist.view.ViewPlatform;
import com.brainless.alchemist.view.actionBar.ActionBar;
import com.brainless.alchemist.view.tab.hierarchy.HierarchyTab;
import com.brainless.alchemist.view.tab.inspector.InspectorTab;
import com.brainless.alchemist.view.tab.report.ReportTab;
import com.brainless.alchemist.view.tab.resources.ResourcesTab;
import com.brainless.alchemist.view.tab.scene.SceneViewTab;
import com.brainless.alchemist.view.util.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import util.LogUtil;

public class Overview extends BorderPane {
	
	@FXML
	private TabPane hierarchyTabPane, inspectorTabPane, resourcesTabPane, sceneViewTabPane;
	
	public Overview() {
		ViewLoader.loadFXMLForControl(this);
	}
	
	@FXML
	public void initialize() {
		setTop(new ActionBar());
		ViewPlatform.hierarchyTabPane = hierarchyTabPane;
		ViewPlatform.inspectorTabPane = inspectorTabPane;
		ViewPlatform.resourcesTabPane = resourcesTabPane;
		ViewPlatform.sceneViewTabPane = sceneViewTabPane;
		
		hierarchyTabPane.getTabs().add(new HierarchyTab());
		inspectorTabPane.getTabs().addAll(new InspectorTab(), new ReportTab());
		resourcesTabPane.getTabs().add(new ResourcesTab());
		sceneViewTabPane.getTabs().add(new SceneViewTab());
	}
	
}
