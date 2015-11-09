package model.world;

import util.geometry.geom2d.Point2D;

public class ToolManager {
	
	static Tool actualTool;
	private static World w;

	private ToolManager(){
		
	}
	
	public static void setTool(Tool tool){
		actualTool = tool;
	}
	
	public static void setWorld(World world){
		w = world;
	}
	
	static World getWorld(){
		return w;
	}
	
	public static Tool getTool(){
		return actualTool;
	}
}
