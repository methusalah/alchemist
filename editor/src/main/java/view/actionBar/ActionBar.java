package main.java.view.actionBar;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;
import main.java.model.EditorPlatform;
import main.java.presentation.actionBar.ActionBarPresenter;
import main.java.presentation.actionBar.ActionBarViewer;
import main.java.view.GameInputListener;
import main.java.view.ViewPlatform;
import main.java.view.common.SceneInputListener;
import main.java.view.util.ViewLoader;

public class ActionBar extends BorderPane implements ActionBarViewer {
	private final ActionBarPresenter presenter;
	private final GameInputListener game;
	private final List<SceneInputListener> savedListeners = new ArrayList<>();

	@FXML
	private Slider tickDurationSlider;
	
	@FXML
	private TextField tickDurationField;
	
	@FXML
	private ToggleButton playButton;
	
	public ActionBar() {
		presenter = new ActionBarPresenter(this);
		game = new GameInputListener(EditorPlatform.getScene());
		ViewLoader.loadFXMLForControl(this);
	}

	@FXML
	private void initialize(){
		// view init
		tickDurationSlider.valueProperty().bindBidirectional(presenter.getMillisPerTickProperty());
		tickDurationField.textProperty().bindBidirectional(presenter.getMillisPerTickProperty(), new NumberStringConverter());
		playButton.selectedProperty().addListener((obs, oldValue, newValue) -> presenter.setRun(newValue));
	}

	@Override
	public void setGameInputListener() {
		savedListeners.addAll(ViewPlatform.getSceneInputManager().getListeners());
		ViewPlatform.getSceneInputManager().getListeners().clear();
		ViewPlatform.getSceneInputManager().addListener(game);
	}

	@Override
	public void removeGameInputlistener() {
		ViewPlatform.getSceneInputManager().getListeners().clear();
		ViewPlatform.getSceneInputManager().getListeners().addAll(savedListeners);
		savedListeners.clear();
	}
}
