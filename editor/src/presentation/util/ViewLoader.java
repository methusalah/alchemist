package presentation.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import util.LogUtil;

public class ViewLoader {

	public static <T> T load(Class<?> controllerClass){
		String fxmlPath;
		if(controllerClass.getAnnotation(FXMLPath.class) != null)
			fxmlPath = controllerClass.getAnnotation(FXMLPath.class).path();
		else
			fxmlPath = controllerClass.getSimpleName() + ".fxml";
		FXMLLoader loader = new FXMLLoader(controllerClass.getResource(fxmlPath));
		T res = null;
		try {
			res = loader.load();
		} catch (IOException e) {
			LogUtil.severe("Can't load " + controllerClass.getResource(fxmlPath));
			e.printStackTrace();
		}
		return res;
	}
	
	public static void loadFXMLForControl(Object control){
        FXMLLoader fxmlLoader = new FXMLLoader(control.getClass().getResource(control.getClass().getSimpleName() + ".fxml"));
        fxmlLoader.setRoot(control);
        fxmlLoader.setController(control);
    	LogUtil.info("location : " + control.getClass().getSimpleName() + ".fxml");
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	public static void loadFXMLForControl(Object control, String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader(control.getClass().getResource(fxmlPath));
        fxmlLoader.setRoot(control);
        fxmlLoader.setController(control);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
}
