package view.controls.propertyEditor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import presenter.InspectorPresenter;

import com.simsilica.es.EntityComponent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;

public class ListEditor extends PropertyEditor{
	
	ListView<String> list;
	final String contentTypeName;
	
	public ListEditor(InspectorPresenter presenter, EntityComponent comp, PropertyDescriptor pd) {
		super(presenter, comp, pd);
		Type t = pd.getReadMethod().getGenericReturnType();
		if(t instanceof ParameterizedType)
			contentTypeName = ((ParameterizedType)t).getActualTypeArguments()[0].getTypeName();
		else
			throw new RuntimeException("List "+pd.getName()+" in component "+comp.getClass().getSimpleName()+" has no readable type argument, which is not supported.");
		list.setCellFactory(TextFieldListCell.forListView());
		
	}

	@Override
	protected void createEditor() {
		HBox box = new HBox(5);
		box.setMaxWidth(Double.MAX_VALUE);
		box.setAlignment(Pos.CENTER_LEFT);
		
		list = new ListView<>();
		list.setMaxWidth(Double.MAX_VALUE);
		setMinHeight(75);
		list.setEditable(true);
		list.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(ListView.EditEvent<String> t) {
				list.getItems().set(t.getIndex(), t.getNewValue());
				applyChange(null);
			}
		});

		list.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(ListView.EditEvent<String> t) {
			}
		});
		list.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		setCenter(list);
	}

	@Override
	protected Object getPropertyValue() {
		List<Object> res = new ArrayList<>();
		for(String s : list.getItems()){
			if(contentTypeName == double.class.getName() || contentTypeName == Double.class.getName())
				res.add(Double.parseDouble(s));
			else if(contentTypeName == String.class.getName())
				res.add(s);
			else if(contentTypeName == boolean.class.getName() || contentTypeName == Boolean.class.getName())
				res.add(Boolean.parseBoolean(s));
			else if(contentTypeName == int.class.getName() || contentTypeName == Integer.class.getName())
				res.add(Integer.parseInt(s));
			else if(contentTypeName == float.class.getName() || contentTypeName == Float.class.getName())
				res.add(Float.parseFloat(s));
		}
		return res;  
	}

	@Override
	protected void setPropertyValue(Object o) {
		List<Object> v = (List<Object>)o;
		list.getItems().clear();
		for(Object s : v)
			list.getItems().add(s.toString());
	}
	
	

}
