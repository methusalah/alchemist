package presentation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import controller.FXMLPath;
import javafx.fxml.FXMLLoader;
import util.LogUtil;

public class ViewLoader {

	public static <T> T load(Class<?> controllerClass){
		String fxmlPath;
		if(controllerClass.getAnnotation(FXMLPath.class) != null)
			fxmlPath = controllerClass.getAnnotation(FXMLPath.class).path();
		else {
			fxmlPath = controllerClass.getSimpleName() + ".fxml";
		}
		FXMLLoader loader = new FXMLLoader(controllerClass.getResource(fxmlPath));
		try {
			loader.setController(controllerClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e1) {
			LogUtil.severe("Can't instanciate " + controllerClass.getName());
		}
		
		T res = null;
		try {
			res = loader.load();
		} catch (IOException e) {
			LogUtil.severe("Can't load " + fxmlPath);
		}
		try {
			controllerClass.getMethod("initialize").invoke(loader.getController());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
		}
		return res;
	}
}
