package view.tab.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import com.simsilica.es.EntityComponent;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import view.util.Consumer3;

public class EnumEditor extends PropertyEditor{
	ChoiceBox<Object> choiceBox;
	
	public EnumEditor(EntityComponent comp, PropertyDescriptor pd, Class<?> enumClass, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
		choiceBox.getItems().setAll(FXCollections.observableArrayList(enumClass.getEnumConstants()));
	}

	@Override
	protected void createEditor() {
		choiceBox = new ChoiceBox<>();
		choiceBox.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		choiceBox.focusedProperty().addListener(e -> setEditionMode());
		setCenter(choiceBox);
	}

	@Override
	protected Object getPropertyValue() {
		return choiceBox.getValue(); 
	}

	@Override
	protected void setPropertyValue(Object o) {
		Object v = o;
		choiceBox.setValue(v);
	}
	
	

}
