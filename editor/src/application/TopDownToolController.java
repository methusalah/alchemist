package application;

import com.jme3x.jfx.injfx.JmeForImageView;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.world.ToolManager;
import util.geometry.geom2d.Point2D;

public class TopDownToolController {
	
	private final JmeForImageView jme;

	public TopDownToolController(JmeForImageView jme) {
		this.jme = jme;
	}

	public void onMousePressed(MouseEvent e){
		if(ToolManager.getTool() != null)
			if(e.getButton() == MouseButton.PRIMARY)
				ToolManager.getTool().onActionStart();
	}

	public void onMouseMoved(MouseEvent e){
		if(ToolManager.getTool() != null)
			ToolManager.getTool().setCoord(new Point2D(e.getX(), e.getY()));
	}

	public void onMouseDragged(MouseEvent e){
	}
	
	public void onMouseReleased(MouseEvent e){
		if(ToolManager.getTool() != null)
			if(e.getButton() == MouseButton.PRIMARY){
				ToolManager.getTool().onActionEnd();
				ToolManager.getTool().onSingleAction();
			}
	}
	
	
	
	public void onMouseScroll(ScrollEvent e){
	}

	public void onKeyPressed(KeyEvent e){
	}
	
	public void onKeyReleased(KeyEvent e){
	}
}
