package presentation.hierarchy;

import presentation.EntityNode;
import presentation.base.Viewer;

public interface HierarchyViewer extends Viewer {
	public void updateSelection(EntityNode node);
}
