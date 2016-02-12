package presentation.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import presenter.InspectorPresenter;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import presentation.util.Consumer3;

import com.simsilica.es.EntityComponent;

public class BooleanEditor extends PropertyEditor{
	
	CheckBox valueBox;
	
	public BooleanEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
	}

	@Override
	protected void createEditor() {
		valueBox = new CheckBox();
		valueBox.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		valueBox.focusedProperty().addListener(e -> setEditionMode());
		setCenter(valueBox);
	}

	@Override
	protected Object getPropertyValue() {
		return valueBox.isSelected(); 
	}

	@Override
	protected void setPropertyValue(Object o) {
		boolean v = (Boolean)o;
		valueBox.setSelected(v);
	}
	
	

}
