package presenter;

import controller.ECS.SceneSelectorState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Command;
import model.ECS.TraversableEntityData;
import model.world.WorldData;
import presenter.common.EntityNode;
import presenter.common.RunState;
import presenter.common.SceneInputManager;
import presenter.util.UserComponentList;
import view.controls.JmeImageView;
import view.instrument.planarStance.PlanarStanceInstruementState;

public class EditorPlatform {
	private static TraversableEntityData entityData;
	private static WorldData worldData;
	private static Command command;
	private static SceneSelectorState sceneSelector;
	private static PlanarStanceInstruementState handle;
	private static JmeImageView scene, preview;
	private static final ObjectProperty<UserComponentList> userComponentListProperty = new SimpleObjectProperty<>();
	private static final ObjectProperty<EntityNode> selectionProperty = new SimpleObjectProperty<>();
	private static final ObjectProperty<RunState> runStateProperty = new SimpleObjectProperty<>();
	private static final SceneInputManager sceneInputManager = new SceneInputManager();
	
	
	static {
		userComponentListProperty.setValue(new UserComponentList());
	}
	
	public static TraversableEntityData getEntityData() {
		return entityData;
	}
	public static void setEntityData(TraversableEntityData entityData) {
		EditorPlatform.entityData = entityData;
	}
	public static WorldData getWorldData() {
		return worldData;
	}
	public static void setWorldData(WorldData worldData) {
		EditorPlatform.worldData = worldData;
	}
	public static SceneSelectorState getSceneSelector() {
		return sceneSelector;
	}
	public static void setSceneSelector(SceneSelectorState sceneSelector) {
		EditorPlatform.sceneSelector = sceneSelector;
	}
	public static PlanarStanceInstruementState getHandle() {
		return handle;
	}
	public static void setHandle(PlanarStanceInstruementState handle) {
		EditorPlatform.handle = handle;
	}
	public static Command getCommand() {
		return command;
	}
	public static void setCommand(Command command) {
		EditorPlatform.command = command;
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
	public static SceneInputManager getSceneInputManager() {
		return sceneInputManager;
	}
}
