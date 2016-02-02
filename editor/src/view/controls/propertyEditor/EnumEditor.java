package view.controls.propertyEditor;

import java.beans.PropertyDescriptor;

import presenter.InspectorPresenter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.simsilica.es.EntityComponent;

public class EnumEditor extends PropertyEditor{
	ChoiceBox<Object> choiceBox;
	
	public EnumEditor(InspectorPresenter presenter, EntityComponent comp, PropertyDescriptor pd, Class<?> enumClass) {
		super(presenter, comp, pd);
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