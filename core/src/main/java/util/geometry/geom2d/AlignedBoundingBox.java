package util.geometry.geom2d;

import java.text.DecimalFormat;
import java.util.ArrayList;

import util.geometry.collections.EdgeRing;
import util.geometry.collections.PointRing;

public class AlignedBoundingBox extends BoundingShape {

	public double maxY;
	public double minY;
	public double maxX;
	public double minX;
	public double height;
	public double width;

	protected AlignedBoundingBox() {

	}

	public AlignedBoundingBox(Point2D corner, double width, double height) {
		ArrayList<Point2D> points = new ArrayList<>();
		points.add(corner);
		points.add(corner.getAddition(width, height));
		computeWith(points);
	}

	public AlignedBoundingBox(Point2D corner1, Point2D corner2) {
		ArrayList<Point2D> points = new ArrayList<>();
		points.add(corner1);
		points.add(corner2);
		computeWith(points);
	}

	public AlignedBoundingBox(ArrayList<Point2D> points) {
		computeWith(points);
	}

	protected void computeWith(ArrayList<Point2D> points) {
		maxX = points.get(0).x;
		minX = points.get(0).x;
		maxY = points.get(0).y;
		minY = points.get(0).y;

		for (Point2D p : points) {
			minX = Math.min(minX, p.x);
			minY = Math.min(minY, p.y);

			maxX = Math.max(maxX, p.x);
			maxY = Math.max(maxY, p.y);
		}
		height = maxY - minY;
		width = maxX - minX;
	}

	public boolean contains(Segment2D s) {
		return contains(s.getStart()) && contains(s.getEnd());
	}

	public boolean contains(Point2D p) {
		return  isInXBounds(p.x) && isInYBounds(p.y);
	}

	public boolean isInXBounds(double x) {
		return x > minX && x < maxX;
	}

	public boolean isInYBounds(double y) {
		return y > minY && y < maxY;
	}

	private static DecimalFormat df = new DecimalFormat("0.00");
	@Override
	public String toString() {
		return "BoundingBox [xmin=" + df.format(minX) + ", xmax=" + df.format(maxX) + ", ymin=" + df.format(minY) + ", ymax=" + df.format(maxY) + "]";
	}

	public PointRing getPoints() {
		PointRing res = new PointRing();
		res.add(new Point2D(minX, minY));
		res.add(new Point2D(maxX, minY));
		res.add(new Point2D(maxX, maxY));
		res.add(new Point2D(minX, maxY));
		return res;
	}

	public EdgeRing getEdges() {
		EdgeRing res = new EdgeRing();
		for (Point2D p : getPoints()) {
			res.add(new Segment2D(p, getPoints().getNext(p)));
		}
		return res;
	}

	public double getArea() {
		return height * width;
	}

	@Override
	public Point2D getCenter() {
		return new Point2D(minX + width / 2, minY + height / 2);
	}
	
	public double getWidth(){
		return width;
	}

	public double getHeight(){
		return height;
	}
	
	public Point2D getLocation(){
		return new Point2D(minX, minY);
	}
}
