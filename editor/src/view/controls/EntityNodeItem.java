package view.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import model.EntityNode;
import view.UIConfig;

public class EntityNodeItem extends TreeItem<EntityNode> {
	
	public EntityNodeItem(EntityNode node) {
		setValue(node);
		if(node != null)
			node.addListener(new ChangeListener<EntityNode>() {
	
				@Override
				public void changed(ObservableValue<? extends EntityNode> observable, EntityNode oldValue, EntityNode newValue) {
					setValue(null);
					setValue(newValue);
				}
			});
		
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
