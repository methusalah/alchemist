package presentation;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import presentation.common.EntityNode;

public class UIConfig {
	public static final ListProperty<Class<? extends EntityComponent>> expandedComponents = new SimpleListProperty<>(FXCollections.observableArrayList());
	public static final List<EntityNode> expandedEntityNodes = new ArrayList<>();
	public static final ObjectProperty<Scene> JavaFXScene = new SimpleObjectProperty<>();
}
