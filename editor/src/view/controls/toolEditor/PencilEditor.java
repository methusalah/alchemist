package view.controls.toolEditor;

import util.event.EventManager;
import util.event.scene.ToolChangedEvent;
import view.controls.custom.IconButton;
import view.controls.toolEditor.parameter.PencilPresenter;
import model.world.PencilTool;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;
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

public class PencilEditor extends BorderPane {

	private final PencilPresenter pencil;
	
	public PencilEditor(PencilPresenter pencil) {
		this.pencil = pencil;
		setLeft(new VBox(getCircleButton(),
				getSquareButton(),
				getDiamondButton(),
				getAirbrushButton(),
				getRoughButton(),
				getNoiseButton()));
		
		setCenter(getSizeSlider());
		setRight(getStrengthSlider());

	}
	private Button getCircleButton(){
		IconButton res = new IconButton("assets/textures/editor/circle_icon.png", "Circle");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				pencil.setShape(SHAPE.Circle);
			}
		});
		return res;
	}
	private Button getSquareButton(){
		IconButton res = new IconButton("assets/textures/editor/square_icon.png", "Square");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				pencil.setShape(SHAPE.Square);
			}
		});
		return res;
	}
	private Button getDiamondButton(){
		IconButton res = new IconButton("assets/textures/editor/diamond_icon.png", "Diamond");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				pencil.setShape(SHAPE.Diamond);
			}
		});
		return res;
	}
	
	private Button getAirbrushButton(){
		IconButton res = new IconButton("assets/textures/editor/airbrush_icon.png", "Airbrush");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				pencil.setMode(MODE.Airbrush);
			}
		});
		return res;
	}
	private Button getRoughButton(){
		IconButton res = new IconButton("assets/textures/editor/rough_icon.png", "Rough");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				pencil.setMode(MODE.Rough);
			}
		});
		return res;
	}
	private Button getNoiseButton(){
		IconButton res = new IconButton("assets/textures/editor/noise_icon.png", "Noise");
		res.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				pencil.setMode(MODE.Noise);
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
				pencil.setSize(PencilTool.MAX_SIZE*newValue.doubleValue());
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
				pencil.setStrength(newValue.doubleValue());
			}
			
		});
		res.getChildren().add(slider);
		return res;
	}	
	
}
