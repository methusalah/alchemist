package main.java.presentation.hierarchy;

import main.java.presentation.EntityNode;
import main.java.presentation.base.Viewer;

public interface HierarchyViewer extends Viewer {
	public void updateSelection(EntityNode node);
}
