package view.controls;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import util.event.ComponentPropertyChanged;
import util.event.EventManager;

import com.simsilica.es.EntityComponent;

public abstract class PropertyEditor extends FlowPane {

	EntityComponent comp;
	PropertyDescriptor pd;
	EventHandler<ActionEvent> actionHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			setChanged();
		}
	};

	
	public PropertyEditor(EntityComponent comp, PropertyDescriptor pd) {
		this.comp = comp;
		this.pd = pd;
		setPrefHeight(25);
		setPadding(new Insets(2, 0, 2, 0));
		
		setAlignment(Pos.CENTER_LEFT);
		Label l = new Label();
		l.setText(pd.getDisplayName());
		l.setMinWidth(150);
		getChildren().add(l);
		
		Pane editor  = new Pane();
		getChildren().add(editor);
		createEditorIn(editor);
		try {
			setPropertyValue(pd.getReadMethod().invoke(comp, new Object[0]));
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected abstract void createEditorIn(Pane p);
	protected abstract Object getPropertyValue();
	protected abstract void setPropertyValue(Object o);
	
	protected void setChanged(){
		EventManager.post(new ComponentPropertyChanged(comp, pd.getName(), getPropertyValue()));
	}
}
