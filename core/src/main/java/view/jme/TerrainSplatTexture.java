/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package view.jme;

import java.util.List;

import com.jme3.material.Material;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;

import app.AppFacade;
import model.world.terrain.TerrainTexture;
import model.world.terrain.atlas.Atlas;

/**
 * @author Benoît
 */
public class TerrainSplatTexture extends Material {

	private final Atlas atlas;
	private final List<TerrainTexture> texturing;

	public boolean transp = false;
	
	public TerrainSplatTexture(Atlas atlas, List<TerrainTexture> texturing) {
		super(AppFacade.getAssetManager(), "matdefs/MyTerrainLighting.j3md");
		this.atlas = atlas;
		this.texturing = texturing;
		updateTextures();
		updateAlpha();
	}
	
	public void updateAlpha(){
		setTexture("AlphaMap", new Texture2D(new Image(Image.Format.RGBA8, atlas.getWidth(), atlas.getHeight(), atlas.getBuffer(0))));
		setTexture("AlphaMap_1", new Texture2D(new Image(Image.Format.RGBA8, atlas.getWidth(), atlas.getHeight(), atlas.getBuffer(1))));
	}
	
	public void updateTextures(){
		int index = 0;
		for (TerrainTexture tt : texturing) {
			Texture diffuse = AppFacade.getAssetManager().loadTexture(tt.getDiffuse());
			diffuse.setWrap(Texture.WrapMode.Repeat);
			diffuse.setAnisotropicFilter(8);
			setTexture(index == 0? "DiffuseMap" : "DiffuseMap_" + index, diffuse);

			double scale = tt.getScale();
			setFloat("DiffuseMap_" + index + "_scale", (float)scale);

			if(tt.getNormal() != null){ 
				Texture normal = AppFacade.getAssetManager().loadTexture(tt.getNormal());
				normal.setAnisotropicFilter(8);
				normal.setWrap(Texture.WrapMode.Repeat);
				setTexture(index == 0? "NormalMap" : "NormalMap_" + index, normal);
			}
			index++;
		}
	}
}
