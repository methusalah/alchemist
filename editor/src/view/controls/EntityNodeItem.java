package view.controls;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import presenter.common.EntityNode;
import view.UIConfig;

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
		        	UIConfig.expandedEntityNodes.add(getValue());
		        else
		        	UIConfig.expandedEntityNodes.remove(getValue());
		});
		
		setExpanded(UIConfig.expandedEntityNodes.contains(getValue()));
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
