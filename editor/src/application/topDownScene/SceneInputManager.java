package application.topDownScene;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class SceneInputManager {
	private final List<SceneInputListener> listeners = new ArrayList<>();
	
	public void addListener(SceneInputListener l){
		listeners.add(l);
	}

	public void removeListener(SceneInputListener l){
		listeners.remove(l);
	}
	
	public void onMousePressed(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMousePressed(e);
	}
	
	public void onMouseReleased(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseReleased(e);
	}

	public void onMouseMoved(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseMoved(e);
	}
	
	public void onMouseDragged(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseDragged(e);
	}
	
	public void onMouseScroll(ScrollEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseScroll(e);
	}

	public void onKeyPressed(KeyEvent e){
		for(SceneInputListener l : listeners)
			l.onKeyPressed(e);
	}
	
	public void onKeyReleased(KeyEvent e){
		for(SceneInputListener l : listeners)
			l.onKeyReleased(e);
	}
	

}
