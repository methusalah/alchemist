package presentation.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import model.EditorPlatform;
import presentation.hierarchy.Hierarchy;

public class Overview {
	
	@FXML 
	private TabPane hierarchieTab;
	
    @FXML 
    private Hierarchy hierarchyController;
	
	@FXML
	private TextField tickDuraction;
	
	
	
	@FXML
	public void initialize() {
		System.out.println("inital");
		
	}
	
	@FXML
	public void stopScene(){
		EditorPlatform.getScene().stop(false);
	}
	
//	public void setComponentList(UserComponentList compList){
//		EditorPlatform.getUserComponentList().setValue(compList);
//	}

	@FXML
	protected void onRunBtnPressed(){
		String text = tickDuraction.getText();
		tickDuraction.setText("button pressed");
	}

}
