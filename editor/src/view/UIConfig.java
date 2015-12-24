package view;

import java.util.ArrayList;
import java.util.List;

import presenter.EntityNode;

import com.simsilica.es.EntityComponent;

public class UIConfig {
	public static final List<Class<? extends EntityComponent>> expandedComponents = new ArrayList<>();
	public static final List<EntityNode> expandedEntityNodes = new ArrayList<>();
}
