package command;

import java.util.ArrayList;
import java.util.List;

import util.geometry.geom2d.Point2D;

public class CommandPlatform {
	public static Point2D target = null;
	public static Point2D thrust = Point2D.ORIGIN;
	public static List<String> abilities = new ArrayList<>();
}
