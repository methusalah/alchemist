package presentation.hierarchy;

import presentation.base.Viewer;
import presentation.common.EntityNode;

public interface HierarchyViewer extends Viewer {
	public void updateSelection(EntityNode node);
}
