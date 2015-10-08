package view;

import java.io.IOException;
import java.lang.reflect.Field;

import com.simsilica.es.EntityComponent;

import application.MainEditor;
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
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.typeEditor.BooleanEditorCtrl;
import view.typeEditor.ColorDataEditorCtrl;
import view.typeEditor.DoubleEditorCtrl;
import view.typeEditor.Point2DEditorCtrl;
import view.typeEditor.Point3DEditorCtrl;
import view.typeEditor.StringEditorCtrl;

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
		
		if(ColorData.class.isAssignableFrom(f.getType())){
			FXMLLoader l = new FXMLLoader(MainEditor.class.getResource("/view/typeEditor/ColorDataEditor.fxml"));
			try {
				l.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			((ColorDataEditorCtrl)l.getController()).setField(comp, f);
			root.getChildren().add(l.getRoot());
			
		} else if(boolean.class.isAssignableFrom(f.getType())){
			FXMLLoader l = new FXMLLoader(MainEditor.class.getResource("/view/typeEditor/BooleanEditor.fxml"));
			try {
				l.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			((BooleanEditorCtrl)l.getController()).setField(comp, f);
			root.getChildren().add(l.getRoot());
			
		} else if(String.class.isAssignableFrom(f.getType())){
			FXMLLoader l = new FXMLLoader(MainEditor.class.getResource("/view/typeEditor/StringEditor.fxml"));
			try {
				l.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			((StringEditorCtrl)l.getController()).setField(comp, f);
			root.getChildren().add(l.getRoot());
			
		} else if(double.class.isAssignableFrom(f.getType())){
			FXMLLoader l = new FXMLLoader(MainEditor.class.getResource("/view/typeEditor/DoubleEditor.fxml"));
			try {
				l.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			((DoubleEditorCtrl)l.getController()).setField(comp, f);
			root.getChildren().add(l.getRoot());
			
		} else if(Point2D.class.isAssignableFrom(f.getType())){
			FXMLLoader l = new FXMLLoader(MainEditor.class.getResource("/view/typeEditor/Point2DEditor.fxml"));
			try {
				l.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			((Point2DEditorCtrl)l.getController()).setField(comp, f);
			root.getChildren().add(l.getRoot());
			
		} else if(Point3D.class.isAssignableFrom(f.getType())){
			FXMLLoader l = new FXMLLoader(MainEditor.class.getResource("/view/typeEditor/Point3DEditor.fxml"));
			try {
				l.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			((Point3DEditorCtrl)l.getController()).setField(comp, f);
			root.getChildren().add(l.getRoot());
		}
		
		title.setText(uiName.isEmpty()? f.getName() : uiName);
		tooltip.setText(info);
	}
}
