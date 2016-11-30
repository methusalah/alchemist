package com.brainless.alchemist.view;

import java.util.ArrayList;
import java.util.List;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.presentation.common.EntityNode;
import com.brainless.alchemist.view.common.SceneInputListener;
import com.brainless.alchemist.view.common.SceneInputManager;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;
import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class ViewPlatform {
	private static JmeImageView scene;
	public static final ListProperty<Class<? extends EntityComponent>> expandedComponents = new SimpleListProperty<>(FXCollections.observableArrayList());
	public static final List<EntityNode> expandedEntityNodes = new ArrayList<>();
	public static final ObjectProperty<Scene> JavaFXScene = new SimpleObjectProperty<>();
	private static final SceneInputManager sceneInputManager = new SceneInputManager();

	public static TabPane hierarchyTabPane, inspectorTabPane, resourcesTabPane, sceneViewTabPane;
	public static SceneInputListener camera;
	private static SceneInputListener gameInputListener = null;

	public static SceneInputManager getSceneInputManager() {
		return sceneInputManager;
	}

	public static SceneInputListener getGameInputListener() {
		return gameInputListener;
	}

	public static void setGameInputListener(SceneInputListener gameInputListener) {
		ViewPlatform.gameInputListener = gameInputListener;
	}
	
	public static void setScene(JmeImageView scene) {
		ViewPlatform.scene = scene;
	}

	public static JmeImageView getScene() {
		return scene;
	}

}
