package presenter;

import com.jme3x.jfx.injfx.JmeForImageView;

import controller.ECS.SceneSelectorState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Command;
import model.ECS.TraversableEntityData;
import model.state.HandleState;
import model.world.WorldData;
import presenter.common.EntityNode;
import presenter.common.RunState;
import presenter.common.SceneInputManager;
import presenter.util.UserComponentList;

public class EditorPlatform {

	private static TraversableEntityData entityData;
	private static WorldData worldData;
	private static Command command;
	private static SceneSelectorState sceneSelector;
	private static HandleState handle;
	private static JmeForImageView scene, preview;
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
	public static HandleState getHandle() {
		return handle;
	}
	public static void setHandle(HandleState handle) {
		EditorPlatform.handle = handle;
	}
	public static Command getCommand() {
		return command;
	}
	public static void setCommand(Command command) {
		EditorPlatform.command = command;
	}
	public static JmeForImageView getScene() {
		return scene;
	}
	public static void setScene(JmeForImageView workScene) {
		EditorPlatform.scene = workScene;
	}
	public static JmeForImageView getPreview() {
		return preview;
	}
	public static void setPreview(JmeForImageView previewScene) {
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
