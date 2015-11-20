package view.controls.toolEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.world.HeightMapTool.OPERATION;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;
import util.LogUtil;
import util.event.EventManager;
import util.event.scene.ToolChangedEvent;
import view.controls.toolEditor.parameter.HeightMapParameter;

public class TerrainEditor extends VBox {

	HeightMapParameter param = new HeightMapParameter();

	public TerrainEditor() {
		getChildren().add(new BorderPane(getNoiseSmoothButton(), null, getRiseLowButton(), null, getUniformResetButton()));
		BorderPane pencil = new BorderPane();
		pencil.setLeft(new VBox(getCircleButton(),
				getSquareButton(),
				getDiamondButton(),
				getAirbrushButton(),
				getRoughButton(),
				getNoiseButton()));
		getChildren().add(pencil);
	}
	
	private Button getRiseLowButton(){
		Button res = new Button("Rise/Low");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setOperation(OPERATION.Raise_Low);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}

	private Button getNoiseSmoothButton(){
		Button res = new Button("Noise/Smooth");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setOperation(OPERATION.Noise_Smooth);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}

	private Button getUniformResetButton(){
		Button res = new Button("Uniform/Reset");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setOperation(OPERATION.Uniform_Reset);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}

	private Button getCircleButton(){
		Button res = new Button("Circle");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setShape(SHAPE.Circle);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}
	private Button getSquareButton(){
		Button res = new Button("Square");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setShape(SHAPE.Square);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}
	private Button getDiamondButton(){
		Button res = new Button("Diamond");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setShape(SHAPE.Diamond);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}
	
	private Button getAirbrushButton(){
		Button res = new Button("Airbrush");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setMode(MODE.Airbrush);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}
	private Button getRoughButton(){
		Button res = new Button("Rough");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setMode(MODE.Rough);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}
	private Button getNoiseButton(){
		Button res = new Button("Noise");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setMode(MODE.Noise);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}
}
