package presentation.resource;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.ES.component.Parenting;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import presentation.util.Dragpool;
import presentation.util.ViewLoader;
import presenter.common.EntityNode;
import presenter.util.BlueprintComparator;
import util.LogUtil;


public class Resources extends AnchorPane {
	
	private ListProperty<Blueprint> blueprintList;
	
	@FXML
	private ListView<Blueprint> blueprintListView;
	
	@FXML
	private VBox resourceVBox;
	
    public Resources() {
    	ViewLoader.loadFXMLForControl(this);
    }
    
	@FXML
	private void initialize() {
		LogUtil.info("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		blueprintList = new SimpleListProperty<Blueprint>(FXCollections.observableArrayList());
		for(Blueprint bp : BlueprintLibrary.getAllBlueprints())
			blueprintList.add(bp);
		blueprintList.sort(new BlueprintComparator());
		
		configureCellFactoryForDragAndDrop(blueprintListView);
	}
	
	public ListProperty<Blueprint> getBlueprintListProperty(){
		return blueprintList;
	}
	
	public void saveEntity(EntityNode ep){
		Blueprint saved = getBlueprintFromPresenter(ep);
		BlueprintLibrary.saveBlueprint(saved);
		
		for(Blueprint bp : blueprintList)
			if(bp.getName().equalsIgnoreCase(saved.getName())){
				blueprintList.set(blueprintList.indexOf(bp), saved);
				return;
			}
		// at this point, the saved blueprint doesn't replace any existing blueprint
		blueprintList.add(saved);
		blueprintList.sort(new BlueprintComparator());
	}
	
	private Blueprint getBlueprintFromPresenter(EntityNode ep){
		// we have to ignore the parenting component, for it is created from the blueprint tree
		List<EntityComponent> comps = new ArrayList<>();
		for(EntityComponent comp : ep.componentListProperty())
			if(!(comp instanceof Parenting))
				comps.add(comp);

		List<Blueprint> children = new ArrayList<>();
		for(EntityNode child : ep.childrenListProperty())
			children.add(getBlueprintFromPresenter(child));
		
		return new Blueprint(ep.nameProperty().getValue(),
				comps,
				children);
	}
	
	private void configureCellFactoryForDragAndDrop(ListView<Blueprint> list) {
		list.setCellFactory(new Callback<ListView<Blueprint>, ListCell<Blueprint>>() {

			@Override
			public ListCell<Blueprint> call(ListView<Blueprint> list) {
				ListCell<Blueprint> cell = new ListCell<Blueprint>() {

					@Override
					protected void updateItem(Blueprint item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getName());
						} else {
							setText(null);
							setGraphic(null);
						}
					}
				};

				cell.setOnDragDetected(e -> {
					Dragpool.setContent(cell.getItem());
	                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                	ClipboardContent content = new ClipboardContent();
                	content.putString("");
                	db.setContent(content);
	                e.consume();
				});
				return cell;
			}
		});
		
		list.setOnDragOver(e -> {
			if (Dragpool.containsType(EntityNode.class))
				e.acceptTransferModes(TransferMode.ANY);
			e.consume();
		});

		list.setOnDragDropped(e -> {
			if (Dragpool.containsType(EntityNode.class))
				saveEntity((EntityNode) Dragpool.grabContent(EntityNode.class));
		});
	}
}
