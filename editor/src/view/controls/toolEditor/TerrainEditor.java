package view.controls.toolEditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.world.HeightMapTool.OPERATION;
import model.world.PencilTool;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;
import util.event.EventManager;
import util.event.scene.ToolChangedEvent;
import view.controls.custom.IconButton;
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
		
		pencil.setCenter(getSizeSlider());
		pencil.setRight(getStrengthSlider());
		getChildren().add(pencil);
	}
	
	private Button getRiseLowButton(){
		IconButton res = new IconButton("assets/textures/editor/rise_low_icon.png", "Rise/Low");
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
		IconButton res = new IconButton("assets/textures/editor/noise_smooth_icon.png", "Noise/Smooth");
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
		IconButton res = new IconButton("assets/textures/editor/uniform_reset_icon.png", "Uniform/Reset");
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
		IconButton res = new IconButton("assets/textures/editor/circle_icon.png", "Circle");
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
		IconButton res = new IconButton("assets/textures/editor/square_icon.png", "Square");
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
		IconButton res = new IconButton("assets/textures/editor/diamond_icon.png", "Diamond");
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
		IconButton res = new IconButton("assets/textures/editor/airbrush_icon.png", "Airbrush");
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
		IconButton res = new IconButton("assets/textures/editor/rough_icon.png", "Rough");
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
		IconButton res = new IconButton("assets/textures/editor/noise_icon.png", "Noise");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				param.setMode(MODE.Noise);
				EventManager.post(new ToolChangedEvent(param));
			}
		});
		return res;
	}
	
	private Node getSizeSlider(){
		VBox res = new VBox();
		res.setMaxWidth(30);
		res.setMaxHeight(Double.MAX_VALUE);
		res.getChildren().add(new Label("Size"));
		Slider slider = new Slider(0, 1, 0.3);
		slider.setOrientation(Orientation.VERTICAL);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				param.setSize(PencilTool.MAX_SIZE*newValue.doubleValue());
				EventManager.post(new ToolChangedEvent(param));
			}
			
		});
		res.getChildren().add(slider);
		return res;
	}
	
	private Node getStrengthSlider(){
		VBox res = new VBox();
		res.setMaxWidth(30);
		res.setMaxHeight(Double.MAX_VALUE);
		res.getChildren().add(new Label("Strength"));
		Slider slider = new Slider(0, 1, 0.3);
		slider.setOrientation(Orientation.VERTICAL);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				param.setStrength(newValue.doubleValue());
				EventManager.post(new ToolChangedEvent(param));
			}
			
		});
		res.getChildren().add(slider);
		return res;
	}
}
