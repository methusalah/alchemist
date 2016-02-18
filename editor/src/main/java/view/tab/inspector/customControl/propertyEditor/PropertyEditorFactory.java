package main.java.view.tab.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import main.java.model.tempImport.ColorData;
import main.java.view.util.Consumer3;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.Fraction;

public class PropertyEditorFactory {

	
	public static PropertyEditor getEditorFor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction){
		if(pd.getPropertyType().isEnum()){
			return new EnumEditor(comp, pd, pd.getPropertyType(), updateCompFunction);
		}
		if(pd.getPropertyType() == Point2D.class){
			return new Point2DEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == Point3D.class){
			return new Point3DEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == double.class){
			return new DoubleEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == int.class){
			return new IntegerEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == boolean.class){
			return new BooleanEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == ColorData.class){
			return new ColorDataEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == String.class){
			return new StringEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == Angle.class){
			return new AngleEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == List.class){
			return new StringListEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == Map.class){
			return new MapEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == EntityId.class){
			return new EntityIdEditor(comp, pd, updateCompFunction);
		}
		if(pd.getPropertyType() == Fraction.class){
			return new FractionEditor(comp, pd, updateCompFunction);
		}
		return null;
	}
}
