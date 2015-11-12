package application.topDownScene;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface SceneInputListener {
	public void onMousePressed(MouseEvent e);
	
	public void onMouseReleased(MouseEvent e);

	public void onMouseMoved(MouseEvent e);
	
	public void onMouseDragged(MouseEvent e);
	
	public void onMouseScroll(ScrollEvent e);

	public void onKeyPressed(KeyEvent e);
	
	public void onKeyReleased(KeyEvent e);
}
