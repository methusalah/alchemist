package view;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import presenter.common.EntityNode;

public class UIConfig {
	public static final ListProperty<Class<? extends EntityComponent>> expandedComponents = new SimpleListProperty<>(FXCollections.observableArrayList());
	public static final List<EntityNode> expandedEntityNodes = new ArrayList<>();
}
