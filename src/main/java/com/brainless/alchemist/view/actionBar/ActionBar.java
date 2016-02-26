package com.brainless.alchemist.view.actionBar;

import java.util.ArrayList;
import java.util.List;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.presentation.actionBar.ActionBarPresenter;
import com.brainless.alchemist.presentation.actionBar.ActionBarViewer;
import com.brainless.alchemist.view.ViewPlatform;
import com.brainless.alchemist.view.common.SceneInputListener;
import com.brainless.alchemist.view.util.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

public class ActionBar extends BorderPane implements ActionBarViewer {
	private final ActionBarPresenter presenter;
	private final List<SceneInputListener> savedListeners = new ArrayList<>();

	@FXML
	private Slider tickDurationSlider;
	
	@FXML
	private TextField tickDurationField;
	
	@FXML
	private ToggleButton playButton;
	
	public ActionBar() {
		presenter = new ActionBarPresenter(this);
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
		if(ViewPlatform.getGameInputListener() != null)
			ViewPlatform.getSceneInputManager().addListener(ViewPlatform.getGameInputListener());
	}

	@Override
	public void removeGameInputlistener() {
		ViewPlatform.getSceneInputManager().getListeners().clear();
		ViewPlatform.getSceneInputManager().getListeners().addAll(savedListeners);
		savedListeners.clear();
	}
}
