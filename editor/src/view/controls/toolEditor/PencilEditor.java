package view.controls.toolEditor;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.world.PencilTool;
import model.world.PencilTool.MODE;
import model.world.PencilTool.SHAPE;
import view.controls.custom.IconButton;

public class PencilEditor extends BorderPane {

	private final PencilTool tool;
	
	public PencilEditor(PencilTool tool) {
		this.tool = tool;
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
		res.setOnAction(e -> tool.setShape(SHAPE.Circle));
		return res;
	}
	private Button getSquareButton(){
		IconButton res = new IconButton("assets/textures/editor/square_icon.png", "Square");
		res.setOnAction(e -> tool.setShape(SHAPE.Square));
		return res;
	}
	private Button getDiamondButton(){
		IconButton res = new IconButton("assets/textures/editor/diamond_icon.png", "Diamond");
		res.setOnAction(e -> tool.setShape(SHAPE.Diamond));
		return res;
	}
	
	private Button getAirbrushButton(){
		IconButton res = new IconButton("assets/textures/editor/airbrush_icon.png", "Airbrush");
		res.setOnAction(e -> tool.setMode(MODE.Airbrush));
		return res;
	}
	private Button getRoughButton(){
		IconButton res = new IconButton("assets/textures/editor/rough_icon.png", "Rough");
		res.setOnAction(e -> tool.setMode(MODE.Rough));
		return res;
	}
	private Button getNoiseButton(){
		IconButton res = new IconButton("assets/textures/editor/noise_icon.png", "Noise");
		res.setOnAction(e -> tool.setMode(MODE.Noise));
		return res;
	}
	
	private Node getSizeSlider(){
		VBox res = new VBox();
		res.setMaxWidth(30);
		res.setMaxHeight(Double.MAX_VALUE);
		res.getChildren().add(new Label("Size"));
		Slider slider = new Slider(0, 1, 0.3);
		slider.setOrientation(Orientation.VERTICAL);
		slider.valueProperty().addListener((observable, oldValue, newValue) -> tool.setSize(PencilTool.MAX_SIZE*newValue.doubleValue()));
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
		slider.valueProperty().addListener((observable, oldValue, newValue) -> tool.setStrength(newValue.doubleValue()));
		res.getChildren().add(slider);
		return res;
	}	
	
}
