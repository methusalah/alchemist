package view.controls.propertyEditor;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import presenter.InspectorPresenter;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import model.ES.richData.ColorData;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.Fraction;

public class PropertyEditorFactory {

	
	public static PropertyEditor getEditorFor(InspectorPresenter presenter, EntityComponent comp, PropertyDescriptor pd){
		if(pd.getPropertyType() == Point2D.class){
			return new Point2DEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == Point3D.class){
			return new Point3DEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == double.class){
			return new DoubleEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == int.class){
			return new IntegerEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == boolean.class){
			return new BooleanEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == ColorData.class){
			return new ColorDataEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == String.class){
			return new StringEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == Angle.class){
			return new AngleEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == List.class){
			return new ListEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == Map.class){
			return new MapEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == EntityId.class){
			return new EntityIdEditor(presenter, comp, pd);
		}
		if(pd.getPropertyType() == Fraction.class){
			return new FractionEditor(presenter, comp, pd);
		}
		return null;
	}
}
