package presentation.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presentation.util.Consumer3;

public class StringListEditor extends PropertyEditor{
	
	ListView<String> list;
	final String contentTypeName;
	
	public StringListEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
		Type t = pd.getReadMethod().getGenericReturnType();
		if(t instanceof ParameterizedType)
			contentTypeName = ((ParameterizedType)t).getActualTypeArguments()[0].getTypeName();
		else
			throw new RuntimeException("List "+pd.getName()+" in component "+comp.getClass().getSimpleName()+" has no readable type argument, which is not supported.");
		list.setCellFactory(TextFieldListCell.forListView());
		
	}

	@Override
	protected void createEditor() {
		list = new ListView<>();
		list.setMaxWidth(Double.MAX_VALUE);
		setMinHeight(100);
		list.setEditable(true);
		list.setOnEditCommit(e -> {
				list.getItems().set(e.getIndex(), e.getNewValue());
				applyChange(null);
		});
		//list.itemsProperty().getValue().addListener((ListChangeListener<String>) e -> applyChange(null));
		
		TextField addField = new TextField();
		Button addButton = new Button("+");
		addButton.setOnAction(e -> {
			if(!addField.getText().isEmpty()){
				list.getItems().add(addField.getText());
				applyChange(null);
			}
		});
		Button removeButton = new Button("-");
		removeButton.setOnAction(e -> {
			if(!list.getSelectionModel().isEmpty()){
				list.getItems().remove(list.getSelectionModel().getSelectedIndex());	
				applyChange(null);
			}
		});
		setCenter(new VBox(list, new BorderPane(addField, null, new BorderPane(null, null, removeButton, null, addButton), null, null)));
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
