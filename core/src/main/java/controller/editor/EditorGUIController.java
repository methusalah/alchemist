/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package controller.editor;

import java.util.List;
import java.util.logging.Logger;

import util.LogUtil;
import model.ModelManager;
import model.builders.entity.MapStyleBuilder;
import model.builders.entity.definitions.BuilderManager;
import model.editor.ToolManager;
import model.editor.tools.Tool;
import app.AppFacade;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import controller.GUIController;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.screen.Screen;

/**
 * @author Benoît
 */
public class EditorGUIController extends GUIController {

	private static final Logger logger = Logger.getLogger(EditorGUIController.class.getName());

	@Inject
	public EditorGUIController() {
		drawer = new EditorGUIDrawer(this);
		AppFacade.getNifty().setIgnoreKeyboardEvents(true);
		AppFacade.getNifty().fromXml("interface/screen.xml", "editor");
	}

	@Override
	public void activate() {
		AppFacade.getNifty().gotoScreen("editor");
		AppFacade.getNifty().update();
		askRedraw();
	}

	@Override
	public void update() {
		if (!AppFacade.getNifty().getCurrentScreen().getScreenId().equals("editor")) {
			throw new RuntimeException("updating editor screen but is not current screen.");
		}
		if (redrawAsked) {
			drawer.draw();
			redrawAsked = false;
		}
	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
	}

	@Override
	public void onStartScreen() {
		logger.info("onStartScreen");
	}

	@Override
	public void onEndScreen() {
		logger.info("onEndScreen");
	}

	@NiftyEventSubscriber(pattern = ".*slider")
	public void onSliderChanged(final String id, final SliderChangedEvent event) {
		Tool actualTooL = ToolManager.getActualTool();
		switch (id) {
			case "sizeslider":
				if (event.getValue() < ToolManager.getActualTool().pencil.size) {
					ToolManager.getActualTool().pencil.decRadius();
				} else if (event.getValue() > ToolManager.getActualTool().pencil.size) {
					ToolManager.getActualTool().pencil.incRadius();
				}
				break;
			case "strslider":
				ToolManager.getActualTool().pencil.strength = event.getValue();
				break;
		}
	}

	@NiftyEventSubscriber(pattern = ".*list")
	public void onListSelectionChanged(final String id, final ListBoxSelectionChangedEvent event) {
		List<Integer> selectionIndices = event.getSelectionIndices();
		if (selectionIndices.isEmpty()) {
			return;
		}
		switch (id) {
			case "selectionlist":
				ToolManager.getActualTool().getSet().set(selectionIndices.get(0));
				break;
		}
	}

	@NiftyEventSubscriber(pattern = ".*dropdown")
	public void onDropDownSelectionChanged(final String id, final DropDownSelectionChangedEvent event) {
		if (!event.getDropDown().isEnabled()) {
			return;
		}
		int selectionIndex = event.getSelectionItemIndex();
		MapStyleBuilder builder = BuilderManager.getAllMapStyleBuilders().get(selectionIndex);
		if (!ModelManager.getBattlefield().getMap().getMapStyleID().equals(builder.getId())) {
			ModelManager.getBattlefield().getMap().setMapStyleID(builder.getId());
			ModelManager.reload();
		}
	}

	public void load() {
		ModelManager.loadBattlefield();
	}

	public void save() {
		ModelManager.saveBattlefield();
	}

	public void newMap() {
		ModelManager.setNewBattlefield();
	}

	public void settings() {
//		MainRTS.appInstance.changeSettings();
	}

	public void toggleGrid() {
//		injector.getInstance(EditorController.class).view.editorRend.toggleGrid();
	}

	public void setCliffTool() {
		ToolManager.setCliffTool();
		askRedraw();
	}

	public void setHeightTool() {
		ToolManager.setHeightTool();
		askRedraw();
	}

	public void setAtlasTool() {
		ToolManager.setAtlasTool();
		askRedraw();
	}

	public void setRampTool() {
		ToolManager.setRampTool();
		askRedraw();
	}

	public void setUnitTool() {
		ToolManager.setUnitTool();
		askRedraw();
	}

	public void setTrincketTool() {
		ToolManager.setTrinketTool();
		askRedraw();
	}

	public void setOperation(String indexString) {
		ToolManager.getActualTool().setOperation(Integer.parseInt(indexString));
		askRedraw();
	}

	public void setSet(String indexString) {
		if (ToolManager.getActualTool().hasSet()) {
			ToolManager.getActualTool().getSet().set(Integer.parseInt(indexString));
		}
		askRedraw();
	}

	public void setRoughMode() {
		ToolManager.getActualTool().pencil.setRoughMode();
		askRedraw();
	}

	public void setAirbrushMode() {
		ToolManager.getActualTool().pencil.setAirbrushMode();
		askRedraw();
	}

	public void setNoiseMode() {
		ToolManager.getActualTool().pencil.setNoiseMode();
		askRedraw();
	}

	public void setSquareShape() {
		ToolManager.getActualTool().pencil.setSquareShape();
		askRedraw();
	}

	public void setDiamondShape() {
		ToolManager.getActualTool().pencil.setDiamondShape();
		askRedraw();
	}

	public void setCircleShape() {
		ToolManager.getActualTool().pencil.setCircleShape();
		askRedraw();
	}
}
