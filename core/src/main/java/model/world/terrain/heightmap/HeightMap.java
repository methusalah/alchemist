package model.world.terrain.heightmap;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.world.terrain.atlas.AtlasLayer;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.geometry.structure.grid.Grid;

public class HeightMap extends Grid<HeightMapNode> {
	
	public HeightMap(int width, int height, Point2D coord) {
		super(width, height, coord);
		for(int i = 0; i < xSize*ySize; i++)
			set(i, new HeightMapNode(i));
	}
	
	public HeightMap(@JsonProperty("width")int width,
			@JsonProperty("height")int height,
			@JsonProperty("flatData")String flatData,
			@JsonProperty("coord")Point2D coord) {
		super(width, height, coord);
		
		int index = 0;
		for (int i = 0; i < width * height; i++) {
			if(flatData.charAt(index) == '-'){
				int nbZero = getIntFromHexString(flatData.substring(index+1, index+9));
				LogUtil.info(flatData.substring(index+1, index+9)+"zeros found : "+nbZero);
				while(nbZero-- > 0)
					set(i++, new HeightMapNode(i, 0));
				i--;
				index += 10;
			} else {
				float f = getFloatFromHexString(flatData.substring(index, index+8));
				set(i, new HeightMapNode(i, f));
				index += 9;
			}
		}

//		for(int i = 0; i < xSize*ySize; i++)
//			set(i, new HeightMapNode(i, flatData[i]));
	}
	
	public Point3D getPos(HeightMapNode height){
		return new Point3D(getCoord(height.getIndex()), height.getElevation());
	}

	public String getFlatData(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size(); i++){
			float f = (float)(get(i).getElevation());
			if(f == 0){
				// grouping of zero values
				int nbZero = 1;
				while(i+nbZero < size() &&
						nbZero < Integer.MAX_VALUE &&
						(float)(get(i+nbZero)).getElevation() == 0){
					nbZero++;
				}
				if(nbZero > 4){
					sb.append("-"+String.format("%08X,", nbZero));
					i = i+nbZero-1;
					continue;
				}
			}
			sb.append(String.format("%08X,", f));
		}
		return sb.toString();
	}
	
	public int getWidth(){
		return xSize();
	}
	
	public int getHeight(){
		return ySize;
	}

	@JsonIgnore
	@Override
	public List<HeightMapNode> getAll() {
		return super.getAll();
	}
	
	public static float getFloatFromHexString(String s) {
	    return (float)((Character.digit(s.charAt(0), 16) << 28) +
	    		(Character.digit(s.charAt(1), 16) << 24) + 
	    		(Character.digit(s.charAt(2), 16) << 20) + 
	    		(Character.digit(s.charAt(3), 16) << 16) +
	    		(Character.digit(s.charAt(4), 16) << 12) + 
	    		(Character.digit(s.charAt(5), 16) << 8) + 
	    		(Character.digit(s.charAt(6), 16) << 4) + 
	    		Character.digit(s.charAt(7), 16));
	}
	public static int getIntFromHexString(String s) {
	    return (int)((Character.digit(s.charAt(0), 16) << 28) +
	    		(Character.digit(s.charAt(1), 16) << 24) + 
	    		(Character.digit(s.charAt(2), 16) << 20) + 
	    		(Character.digit(s.charAt(3), 16) << 16) +
	    		(Character.digit(s.charAt(4), 16) << 12) + 
	    		(Character.digit(s.charAt(5), 16) << 8) + 
	    		(Character.digit(s.charAt(6), 16) << 4) + 
	    		Character.digit(s.charAt(7), 16));
	}

}
