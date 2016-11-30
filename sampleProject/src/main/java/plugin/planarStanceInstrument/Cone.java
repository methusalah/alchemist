package plugin.planarStanceInstrument;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.MyMesh;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;

public class Cone extends MyMesh {
	public Cone(double radius, double height, int sampleCount) {
		
		double angle = 0;
		double step = AngleUtil.FULL/sampleCount;
		Point2D p = new Point2D(radius, 0);
		int i = 0;
		while(angle < AngleUtil.FULL){
			Point2D before = p;
			Point2D after = p.getRotation(step);
			p = after;
			
			// base face
			vertices.add(new Point3D(before.get3D(0)));
			vertices.add(new Point3D(after.get3D(0)));
			vertices.add(Point3D.ORIGIN);
			
			normals.add(new Point3D(0, 0, -1));
			normals.add(new Point3D(0, 0, -1));
			normals.add(new Point3D(0, 0, -1));
			
			textCoord.add(before);
			textCoord.add(after);
			textCoord.add(Point2D.ORIGIN);
			
			indices.add(i++);
			indices.add(i++);
			indices.add(i++);

			// side face
			vertices.add(new Point3D(before.get3D(0)));
			vertices.add(new Point3D(after.get3D(0)));
			vertices.add(new Point3D(0, 0, height));
			
			normals.add(after.getSubtraction(before).get3D(0).getNormalized().getCross(new Point3D(0, 0, height)).getSubtraction(before.get3D(0)).getNormalized());
			normals.add(after.getSubtraction(before).get3D(0).getNormalized().getCross(new Point3D(0, 0, height)).getSubtraction(before.get3D(0)).getNormalized());
			normals.add(after.getSubtraction(before).get3D(0).getNormalized().getCross(new Point3D(0, 0, height)).getSubtraction(before.get3D(0)).getNormalized());
			
			textCoord.add(before);
			textCoord.add(after);
			textCoord.add(Point2D.ORIGIN);
			
			indices.add(i++);
			indices.add(i++);
			indices.add(i++);
			
			
			angle += step;
		}
	}
}