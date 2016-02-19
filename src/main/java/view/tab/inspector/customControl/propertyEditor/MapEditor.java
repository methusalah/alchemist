package view.tab.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import util.LogUtil;
import view.util.Consumer3;

public class MapEditor extends PropertyEditor{
	
	ObservableList<Map.Entry<String, Object>> items;
	TableView<Map.Entry<String,Object>> table;
	final String contentTypeName;

	public MapEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
		Type t = pd.getReadMethod().getGenericReturnType();
		if(t instanceof ParameterizedType){
			if(((ParameterizedType)t).getActualTypeArguments()[0].getTypeName() != String.class.getName())
				throw new RuntimeException(getClass().getSimpleName()+" only support map with String as key");
			contentTypeName = ((ParameterizedType)t).getActualTypeArguments()[1].getTypeName();
		} else
			throw new RuntimeException("Map "+pd.getName()+" in component "+comp.getClass().getSimpleName()+" has no readable type argument, whici is not supported.");
		
		TableColumn<Map.Entry<String, Object>, String> column1 = new TableColumn<>("Key");
		column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Object>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Object>, String> p) {
                // this callback returns property for just one cell, you can't use a loop here
                // for first column we use key
                return new SimpleStringProperty(p.getValue().getKey().toString());
            }
        });
        column1.setOnEditCommit(new EventHandler<CellEditEvent<Map.Entry<String, Object>, String>>() {

			@Override
			public void handle(CellEditEvent<Entry<String, Object>, String> arg0) {
				items.set(arg0.getTablePosition().getRow(), new MyEntry<String,  Object>(arg0.getNewValue(), arg0.getRowValue().getValue()));
				applyChange(null);
			}
		});

        TableColumn<Map.Entry<String, Object>, String> column2 = new TableColumn<>("Value");
		column2.setCellFactory(TextFieldTableCell.forTableColumn());
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Object>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Object>, String> p) {
                // for second column we use value
                return new SimpleStringProperty(p.getValue().getValue().toString());
            }
        });
        column2.setOnEditCommit(new EventHandler<CellEditEvent<Map.Entry<String, Object>, String>>() {

			@Override
			public void handle(CellEditEvent<Entry<String, Object>, String> arg0) {
				items.set(arg0.getTablePosition().getRow(), new MyEntry<String,  Object>(arg0.getRowValue().getKey(), Boolean.parseBoolean(arg0.getNewValue())));
				applyChange(null);
			}
		});

        
        table.setItems(items);
        table.getColumns().setAll(column1, column2);
        
	}

	@Override
	protected void createEditor() {
        items = FXCollections.observableArrayList();
		HBox box = new HBox(5);
		box.setMaxWidth(Double.MAX_VALUE);
		box.setAlignment(Pos.CENTER_LEFT);
		
		table = new TableView<>();
		table.setMaxWidth(Double.MAX_VALUE);
		setMinHeight(100);
		table.setEditable(true);
		
		table.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		setCenter(table);
	}

	@Override
	protected Object getPropertyValue() {
		HashMap<String, Object> res = new HashMap<>();
		for(Entry<String, Object> entry : items){
			res.put(entry.getKey(), entry.getValue());
			LogUtil.info("entry "+entry);
		}
		return res;
	}

	@Override
	protected void setPropertyValue(Object o) {
		HashMap<String, Object> map = (HashMap<String, Object>)o;
		items.clear();
		items.addAll(map.entrySet());
		
	}
	
	final class MyEntry<K, V> implements Map.Entry<K, V> {
	    private final K key;
	    private V value;

	    public MyEntry(K key, V value) {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public K getKey() {
	        return key;
	    }

	    @Override
	    public V getValue() {
	        return value;
	    }

	    @Override
	    public V setValue(V value) {
	        V old = this.value;
	        this.value = value;
	        return old;
	    }
	}	
}
