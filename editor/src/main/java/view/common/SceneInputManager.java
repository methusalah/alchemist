package main.java.view.common;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class SceneInputManager {
	private final List<SceneInputListener> listeners = new ArrayList<>();
	public List<SceneInputListener> getListeners() {
		return listeners;
	}

	private SceneInputListener concurrentListener;
	
	public SceneInputListener getConcurrentListener() {
		return concurrentListener;
	}

	public void setConcurrentListener(SceneInputListener concurrentListener) {
		this.concurrentListener = concurrentListener;
	}

	public void addListener(SceneInputListener l){
		listeners.add(l);
	}

	public void removeListener(SceneInputListener l){
		listeners.remove(l);
	}
	
	public boolean hasListener(SceneInputListener l){
		return listeners.contains(l);
	}
	
	public void onMousePressed(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMousePressed(e);
		if(concurrentListener != null)
			concurrentListener.onMousePressed(e);
	}
	
	public void onMouseReleased(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseReleased(e);
		if(concurrentListener != null)
			concurrentListener.onMouseReleased(e);
	}

	public void onMouseMoved(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseMoved(e);
		if(concurrentListener != null)
			concurrentListener.onMouseMoved(e);
	}
	
	public void onMouseDragged(MouseEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseDragged(e);
		if(concurrentListener != null)
			concurrentListener.onMouseDragged(e);
	}
	
	public void onMouseScroll(ScrollEvent e){
		for(SceneInputListener l : listeners)
			l.onMouseScroll(e);
		if(concurrentListener != null)
			concurrentListener.onMouseScroll(e);
	}

	public void onKeyPressed(KeyEvent e){
		for(SceneInputListener l : listeners)
			l.onKeyPressed(e);
		if(concurrentListener != null)
			concurrentListener.onKeyPressed(e);
	}
	
	public void onKeyReleased(KeyEvent e){
		for(SceneInputListener l : listeners)
			l.onKeyReleased(e);
		if(concurrentListener != null)
			concurrentListener.onKeyReleased(e);
	}
}
