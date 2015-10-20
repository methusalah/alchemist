package view.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import model.EntityPresenter;
import view.UIConfig;

public class EntityNodeItem extends TreeItem<EntityPresenter> {
	
	public EntityNodeItem(EntityPresenter ep) {
		setValue(ep);
		if(ep != null){
			ep.nameProperty().addListener(new ChangeListener<String>() {
	
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					setValue(null);
					setValue(ep);
				}
			});
			ep.childrenListProperty().addListener(new ListChangeListener<EntityPresenter>() {

				@Override
				public void onChanged(Change<? extends EntityPresenter> c) {
					if(c.wasAdded()){
						for(EntityPresenter added : c.getAddedSubList())
							getChildren().add(new EntityNodeItem(added));
					} else if (c.wasRemoved()){
						for(EntityPresenter removed : c.getAddedSubList()){
							List<TreeItem<EntityPresenter>> toRemove = new ArrayList<>();
							for(TreeItem<EntityPresenter> item : getChildren())
								if(item.getValue() == removed)
									toRemove.add(item);
							getChildren().removeAll(toRemove);
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

}
