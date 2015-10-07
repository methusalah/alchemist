package view;

import java.io.IOException;
import java.lang.reflect.Field;

import com.simsilica.es.EntityComponent;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.ES.richData.ColorData;
import model.ES.serial.EditorInfo;
import util.LogUtil;
import view.typeEditor.DoubleEditorCtrl;

public class FieldEditorCtrl {
	@FXML
	private Label title;
	
	@FXML
	private HBox root;
	
	@FXML
	private Tooltip tooltip;
	
	
	
	@FXML
	private void initialize() {
		
	}
	
	public void setField(EntityComponent comp, Field f){
		String uiName = "", info = "";
		if(f.getAnnotation(EditorInfo.class) != null){
			uiName = f.getAnnotation(EditorInfo.class).UIname();
			info = f.getAnnotation(EditorInfo.class).info();
		}
		
		LogUtil.info("type "+f.getType());
		if(ColorData.class.isAssignableFrom(f.getType())){
			ColorPicker cp = new ColorPicker();
			ColorData c = null;
			try {
				c = (ColorData)f.get(comp);
				cp.setValue(new Color(c.a, c.r, c.g, c.b));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			root.getChildren().add(cp);
		} else if(double.class.isAssignableFrom(f.getType())){
			FXMLLoader l = new FXMLLoader(Main.class.getResource("/view/typeEditor/DoubleEditor.fxml"));
			try {
				l.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			LogUtil.info("loader : "+l.getController());
			((DoubleEditorCtrl)l.getController()).setField(comp, f);
			root.getChildren().add(l.getRoot());
		}
		
		title.setText(uiName.isEmpty()? f.getName() : uiName);
		tooltip.setText(info);
	}
}
