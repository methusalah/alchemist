package view;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import application.MainEditor;

import com.jme3x.jfx.injfx.JmeForImageView;

public class View {
	public final InspectorView inspectorView;
	public final HierarchyView hierarchyView;
	
	public View(Stage stage, JmeForImageView jme) {
		inspectorView = new InspectorView();
		hierarchyView = new HierarchyView();
		stage.setTitle("Entity Editor");
		
		BorderPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainEditor.class.getResource("/view/Overview.fxml")); 
		try {
			root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			
			Pane p = new Pane();
			p.setMaxWidth(Double.MAX_VALUE);
			p.setMaxHeight(Double.MAX_VALUE);
			p.setStyle("-fx-background-color: red");
			root.setCenter(p);
			
			ImageView image = new ImageView();
			image.fitHeightProperty().bind(p.heightProperty());
			image.fitWidthProperty().bind(p.widthProperty());
			image.setStyle("-fx-background-color: blue");
			p.getChildren().add(image);

			jme.bind(image);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			      public void handle(WindowEvent e){
					jme.stop(true);
			      }
			});

//			bindOtherControls(jme, controller);
			
			stage.setScene(scene);
			stage.show();
			root.setRight(inspectorView);
			root.setLeft(hierarchyView);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	

	
//	static void bindOtherControls(JmeForImageView jme, Controller controller) {
//		controller.bgColor.valueProperty().addListener((ov, o, n) -> {
//			jme.enqueue((jmeApp) -> {
//				jmeApp.getViewPort().setBackgroundColor(new ColorRGBA((float)n.getRed(), (float)n.getGreen(), (float)n.getBlue(), (float)n.getOpacity()));
//				return null;
//			});
//		});
//		controller.bgColor.setValue(Color.LIGHTGRAY);
//
//		controller.showStats.selectedProperty().addListener((ov, o, n) -> {
//			jme.enqueue((jmeApp) -> {
//				jmeApp.setDisplayStatView(n);
//				jmeApp.setDisplayFps(n);
//				return null;
//			});
//		});
//		controller.showStats.setSelected(!controller.showStats.isSelected());
//
//		controller.fpsReq.valueProperty().addListener((ov, o, n) -> {
//			jme.enqueue((jmeApp) -> {
//				AppSettings settings = new AppSettings(false);
//				settings.setFullscreen(false);
//				settings.setUseInput(false);
//				settings.setFrameRate(n.intValue());
//				settings.setCustomRenderer(com.jme3x.jfx.injfx.JmeContextOffscreenSurface.class);
//				jmeApp.setSettings(settings);
//				jmeApp.restart();
//				return null;
//			});
//		});
//		controller.fpsReq.setValue(30);
//	}
}
