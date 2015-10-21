package view.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import model.EntityPresenter;
import model.ES.component.ToRemove;
import util.LogUtil;
import view.UIConfig;

public class EntityNodeItem extends TreeItem<EntityPresenter> {
	
	public EntityNodeItem(EntityPresenter ep) {
		setValue(ep);
		if(ep != null){
			ep.childrenListProperty().addListener(new ListChangeListener<EntityPresenter>() {

				@Override
				public void onChanged(Change<? extends EntityPresenter> c) {
					while(c.next()){
						if(c.wasAdded()){
							for(EntityPresenter added : c.getAddedSubList()){
								addChild(added);
							}
						} else if (c.wasRemoved()){
							for(EntityPresenter removed : c.getRemoved()){
								removeChild(removed);
							}
						}
					}
				}
			});
			
		}
		
		expandedProperty().addListener(new ChangeListener<Boolean>() {
			
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if(newValue)
		        	UIConfig.expandedEntityNodes.add(getValue());
		        else
		        	UIConfig.expandedEntityNodes.remove(getValue());
		    }
		});
		
		setExpanded(UIConfig.expandedEntityNodes.contains(getValue()));
	}
	
	private void addChild(EntityPresenter ep){
		EntityNodeItem newChild = new EntityNodeItem(ep); 
		getChildren().add(newChild);
		for(EntityPresenter child : ep.childrenListProperty())
			newChild.addChild(child);
	}
	
	private void removeChild(EntityPresenter ep){
		LogUtil.info("remove from node");
		EntityNodeItem toRemove = null;
		for(TreeItem<EntityPresenter> node : getChildren())
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
		for(TreeItem<EntityPresenter> child : getChildren())
			((EntityNodeItem)child).removeAllChildren();
		getChildren().clear();
	}

}
