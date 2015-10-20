package view;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import model.EntityPresenter;

public class UIConfig {
	public static final List<Class<? extends EntityComponent>> expandedComponents = new ArrayList<>();
	public static final List<EntityPresenter> expandedEntityNodes = new ArrayList<>();
	public static EntityPresenter selectedEntityNode;
}
