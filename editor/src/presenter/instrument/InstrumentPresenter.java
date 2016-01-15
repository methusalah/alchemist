package presenter.instrument;

import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

public interface InstrumentPresenter {
	public Point3D getPosition();
	public double getOrientation();
	public void startDrag(Point2D screenCoord);
	public void drag(Point2D screenCoord);
	public void stopDrag();
}
