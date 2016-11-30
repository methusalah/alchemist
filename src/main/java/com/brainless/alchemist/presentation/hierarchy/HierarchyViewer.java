package com.brainless.alchemist.presentation.hierarchy;

import com.brainless.alchemist.presentation.base.Viewer;
import com.brainless.alchemist.presentation.common.EntityNode;

public interface HierarchyViewer extends Viewer {
	public void updateSelection(EntityNode node);
}
