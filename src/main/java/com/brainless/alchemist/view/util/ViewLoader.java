package com.brainless.alchemist.view.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
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
	
	/**
	 * This method will load an FXML file with the name of the given object class,
	 * concatenated with the fxml file extension and localized in the same package.
	 * @param control
	 */
	public static void loadFXMLForControl(Object control){
        FXMLLoader fxmlLoader = new FXMLLoader(control.getClass().getResource(control.getClass().getSimpleName() + ".fxml"));
        fxmlLoader.setRoot(control);
        fxmlLoader.setController(control);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	/**
	 * This method will load an FXML file localized in the path given in parameter, relatively
	 * to the given object package.
	 * @param control
	 */
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
