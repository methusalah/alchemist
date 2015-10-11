package application;
	
import java.io.IOException;

import com.simsilica.es.base.DefaultEntityData;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.EntityIntrospector;
import model.Hierarchy;
import model.ES.component.Naming;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.BlueprintCreator;
import util.LogUtil;
import view.HierarchyView;
import view.InspectorView;


public class MainEditor extends Application {
	private Stage primaryStage;
	BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		LogUtil.init();
		DefaultEntityData ed = new DefaultEntityData();
		BlueprintCreator.setEntityData(ed);
		BlueprintCreator.create("player ship", null);
		BlueprintCreator.create("enemy", null);
		BlueprintCreator.create("sun", null);

		InspectorView iv = new InspectorView();
		HierarchyView hv = new HierarchyView();
		
		
		
		EntityIntrospector i = new EntityIntrospector(ed, iv);
		i.addComponentToScan(Naming.class);
		i.addComponentToScan(PlanarStance.class);
		Hierarchy h = new Hierarchy(ed);
		
		i.inspect(h.baseNodes.get(0).parent);
		
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Entity Editor");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainEditor.class.getResource("/view/Overview.fxml")); 
		try {
			root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		root.setRight(iv);
		
		hv.update(h.baseNodes);
		root.setLeft(hv);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
