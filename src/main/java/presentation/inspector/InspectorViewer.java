package presentation.inspector;

import com.simsilica.es.EntityComponent;

import presentation.base.Viewer;
import presentation.common.EntityNode;

public interface InspectorViewer extends Viewer {
	public void inspectNewEntity(EntityNode ep);
	public void updateComponentEditor(EntityComponent comp);
	public void addComponentEditor(EntityComponent comp);
	public void removeComponentEditor(EntityComponent comp);
}
