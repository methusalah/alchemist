package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.ECS.data.TraversableEntityData;
import model.ECS.pipeline.PipelineManager;
import model.state.SceneSelectorState;
import presentation.actionBar.RunState;
import presentation.common.EntityNode;
import view.instrument.planarStance.PlanarStanceInstrumentState;
import view.tab.scene.customControl.JmeImageView;
import view.util.UserComponentList;

public class EditorPlatform {
	private static TraversableEntityData entityData;
	private static SceneSelectorState sceneSelector;
	private static PlanarStanceInstrumentState handle;
	private static JmeImageView scene, preview;
	private static PipelineManager pipelineManager;
	private static final ObjectProperty<UserComponentList> userComponentListProperty = new SimpleObjectProperty<>();
	private static final ObjectProperty<EntityNode> selectionProperty = new SimpleObjectProperty<>();
	private static final ObjectProperty<RunState> runStateProperty = new SimpleObjectProperty<>();
	
	static {
		userComponentListProperty.setValue(new UserComponentList());
	}
	
	public static TraversableEntityData getEntityData() {
		return entityData;
	}
	
	public static void setEntityData(TraversableEntityData entityData) {
		EditorPlatform.entityData = entityData;
	}
	
	public static SceneSelectorState getSceneSelector() {
		return sceneSelector;
	}
	
	public static void setSceneSelector(SceneSelectorState sceneSelector) {
		EditorPlatform.sceneSelector = sceneSelector;
	}
	
	public static PlanarStanceInstrumentState getHandle() {
		return handle;
	}
	
	public static void setHandle(PlanarStanceInstrumentState handle) {
		EditorPlatform.handle = handle;
	}

	public static JmeImageView getScene() {
		return scene;
	}

	public static void setScene(JmeImageView workScene) {
		EditorPlatform.scene = workScene;
	}
	
	public static JmeImageView getPreview() {
		return preview;
	}
	
	public static void setPreview(JmeImageView previewScene) {
		EditorPlatform.preview = previewScene;
	}
	
	public static ObjectProperty<EntityNode> getSelectionProperty() {
		return selectionProperty;
	}
	
	public static ObjectProperty<RunState> getRunStateProperty() {
		return runStateProperty;
	}
	
	public static ObjectProperty<UserComponentList> getUserComponentList() {
		return userComponentListProperty;
	}

	public static PipelineManager getPipelineManager() {
		return pipelineManager;
	}

	public static void setPipelineManager(PipelineManager pipelineManager) {
		EditorPlatform.pipelineManager = pipelineManager;
	}
}
