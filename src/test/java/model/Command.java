package model;

import java.util.ArrayList;
import java.util.List;

import util.geometry.geom2d.Point2D;

public class Command {

	public Point2D target = null;
	public Point2D thrust = Point2D.ORIGIN;
	public List<String> abilities = new ArrayList<>();
}
