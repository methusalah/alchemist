package presentation.hierarchy;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import presentation.util.ViewLoader;

public class Hierarchy extends BorderPane {
	
	public Hierarchy() {
		ViewLoader.loadFXMLForControl(this);
	}
//	public void createNewEntity(){
//		EntityId eid = EditorPlatform.getEntityData().createEntity();
//		EditorPlatform.getEntityData().setComponent(eid, new Naming("Unamed entity"));
//	}
//
//	public void createNewEntity(Blueprint bp, EntityNode parent){
//		bp.createEntity(EditorPlatform.getEntityData(), parent == null? null : parent.getEntityId());
//	}
//	
//	public void removeEntity(){
//		EntityNode nodeToRemove = EditorPlatform.getSelectionProperty().getValue();
//		for(EntityNode childNode : nodeToRemove.childrenListProperty()){
//			EditorPlatform.getEntityData().removeEntity(childNode.getEntityId());
//		}
//		EditorPlatform.getEntityData().removeEntity(nodeToRemove.getEntityId());
//	}
	
}
