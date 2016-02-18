package main.java.presentation.inspector;

import com.simsilica.es.EntityComponent;

import main.java.presentation.EntityNode;
import main.java.presentation.base.Viewer;

public interface InspectorViewer extends Viewer {
	public void inspectNewEntity(EntityNode ep);
	public void updateComponentEditor(EntityComponent comp);
	public void addComponentEditor(EntityComponent comp);
	public void removeComponentEditor(EntityComponent comp);
}
