package view.typeEditor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.simsilica.es.EntityComponent;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.ES.component.motion.PlanarStance;
import model.ES.richData.ColorData;
import model.ES.serial.EditorInfo;
import util.LogUtil;

public class DoubleEditorCtrl {
	private EntityComponent comp;
	private Field field;
	
	@FXML
	private TextField textField ;

	@FXML
	private void initialize() {
		
	}
	
	public void setField(EntityComponent comp, Field field){
		this.comp = comp;
		this.field = field;
		try {
			textField.setText(Double.toString(field.getDouble(comp)));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void changeValue(){
		LogUtil.info("orientation : "+((PlanarStance)comp).orientation);
	}
}
