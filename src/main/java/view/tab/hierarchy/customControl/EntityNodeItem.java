package view.tab.hierarchy.customControl;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import presentation.EntityNode;
import view.ViewPlatform;

public class EntityNodeItem extends TreeItem<EntityNode> {
	
	public EntityNodeItem(EntityNode ep) {
		setValue(ep);
		if(ep != null){
			ep.childrenListProperty().addListener((ListChangeListener.Change<? extends EntityNode> c) ->{
					while(c.next()){
						if(c.wasAdded()){
							for(EntityNode added : c.getAddedSubList()){
								addChild(added);
							}
						} else if (c.wasRemoved()){
							for(EntityNode removed : c.getRemoved()){
								removeChild(removed);
							}
						}
					}
			});
		}
		
		expandedProperty().addListener((observable, oldValue, newValue) -> {
		        if(newValue)
		        	ViewPlatform.expandedEntityNodes.add(getValue());
		        else
		        	ViewPlatform.expandedEntityNodes.remove(getValue());
		});
		
		setExpanded(ViewPlatform.expandedEntityNodes.contains(getValue()));
	}
	
	private void addChild(EntityNode ep){
		EntityNodeItem newChild = new EntityNodeItem(ep); 
		getChildren().add(newChild);
		for(EntityNode child : ep.childrenListProperty())
			newChild.addChild(child);
	}
	
	private void removeChild(EntityNode ep){
		EntityNodeItem toRemove = null;
		for(TreeItem<EntityNode> node : getChildren())
			if(node.getValue() == ep){
				toRemove = (EntityNodeItem)node;
				break;
			}
		if(toRemove != null){
			getChildren().remove(toRemove);
			toRemove.removeAllChildren();
		}
	}

	private void removeAllChildren(){
		for(TreeItem<EntityNode> child : getChildren())
			((EntityNodeItem)child).removeAllChildren();
		getChildren().clear();
	}

}
