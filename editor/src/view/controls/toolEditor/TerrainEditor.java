package view.controls.toolEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import model.world.HeightMapTool.OPERATION;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;
import util.LogUtil;
import util.event.EventManager;
import util.event.scene.ToolChangedEvent;
import view.controls.toolEditor.parameter.HeightMapParameter;

public class TerrainEditor extends Pane {


	public TerrainEditor() {
		Button b = new Button("test");
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				LogUtil.info("kljnmkjmkjbkfbg,nbx:d,f:");
				EventManager.post(new ToolChangedEvent(new HeightMapParameter(OPERATION.Raise_Low, 4, 0.4, SHAPE.Circle, MODE.Airbrush)));
			}
		});
		
		getChildren().add(b);
	}
}
