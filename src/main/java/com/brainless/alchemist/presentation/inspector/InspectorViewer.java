package com.brainless.alchemist.presentation.inspector;

import com.brainless.alchemist.presentation.base.Viewer;
import com.brainless.alchemist.presentation.common.EntityNode;
import com.simsilica.es.EntityComponent;

public interface InspectorViewer extends Viewer {
	public void inspectNewEntity(EntityNode ep);
	public void updateComponentEditor(EntityComponent comp);
	public void addComponentEditor(EntityComponent comp);
	public void removeComponentEditor(EntityComponent comp);
}
