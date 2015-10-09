package view.controls;

import java.beans.PropertyDescriptor;
import java.io.IOException;

import com.simsilica.es.EntityComponent;

import javafx.fxml.FXMLLoader;
import model.ES.richData.Angle;
import model.ES.richData.ColorData;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import view.typeEditor.BooleanEditorCtrl;
import view.typeEditor.ColorDataEditorCtrl;
import view.typeEditor.DoubleEditorCtrl;
import view.typeEditor.Point2DEditorCtrl;
import view.typeEditor.Point3DEditorCtrl;
import view.typeEditor.StringEditorCtrl;
import application.MainEditor;

public class PropertyEditorFactory {

	
	public static PropertyEditor getEditorFor(EntityComponent comp, PropertyDescriptor pd){
		if(pd.getPropertyType() == Point2D.class){
			return new Point2DEditor(comp, pd);
		}
		if(pd.getPropertyType() == Point3D.class){
			return new Point3DEditor(comp, pd);
		}
		if(pd.getPropertyType() == double.class){
			return new DoubleEditor(comp, pd);
		}
		if(pd.getPropertyType() == int.class){
			return new IntegerEditor(comp, pd);
		}
		if(pd.getPropertyType() == boolean.class){
			return new BooleanEditor(comp, pd);
		}
		if(pd.getPropertyType() == ColorData.class){
			return new ColorDataEditor(comp, pd);
		}
		if(pd.getPropertyType() == String.class){
			return new StringEditor(comp, pd);
		}
		if(pd.getPropertyType() == Angle.class){
			return new AngleEditor(comp, pd);
		}
		return null;
	}
}
