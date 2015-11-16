package model.world.terrain.atlas;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import util.LogUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores and manage layers of texture to paint on the ground. Atlas itself doesn't know the textures, and provides only alpha channels used by the view to draw
 * and blend textures on a multiple material. This class contains also methods for serialization/deserialization by Byte, has the data may be huge in a more
 * common XML format.
 */
public class Atlas {
	private static final int LAYER_COUNT = 8;
	public static final int RESOLUTION_RATIO = 8;

	private final int width, height;
	private final List<AtlasLayer> layers = new ArrayList<>();
	private final List<ByteBuffer> buffers = new ArrayList<>();

	private boolean toUpdate = false;

	public Atlas(@JsonProperty("width")int width,
			@JsonProperty("height")int height,
			@JsonProperty("roughData")byte[] roughData) {
		this.width = width;
		this.height = height;
		int index = 0;
		for (int i = 0; i < LAYER_COUNT; i++) {
			AtlasLayer l = new AtlasLayer(width, height);
			for (int xy = 0; xy < width * height; xy++) {
				l.setByte(xy, roughData[index++]);
			}
			layers.add(l);
		}
		buffers.add(buildBuffer(0));
		buffers.add(buildBuffer(1));
		toUpdate = true;
	}
	
	public Atlas(int mapWidth, int mapHeight) {
		width = mapWidth * RESOLUTION_RATIO;
		height = mapHeight * RESOLUTION_RATIO;
		layers.add(new AtlasLayer(width, height, AtlasLayer.MAX_VALUE));
		for (int i = 1; i < LAYER_COUNT; i++) {
			layers.add(new AtlasLayer(width, height, 0));
		}
		buffers.add(buildBuffer(0));
		buffers.add(buildBuffer(1));
	}

	private ByteBuffer buildBuffer(int index) {
		ByteBuffer res = ByteBuffer.allocateDirect(width * height * 4);
		int firstLayerIndex = index * 4;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				res.asIntBuffer().put(y * width + x, getBufferVal(x, y, firstLayerIndex));
			}
		}
		return res;
	}

	public ByteBuffer getBuffer(int index) {
		return buffers.get(index);
	}

	public byte[] getRoughData(){
		byte[] bytes = new byte[width * height * LAYER_COUNT];
		int index = 0;
		for (AtlasLayer l : layers) {
			for (Byte b : l.getBytes()) {
				bytes[index++] = b;
			}
		}
		return bytes;
	}
	
//	public void saveToFile(String fileName, String suffix) {
//		byte[] bytes = new byte[width * height * LAYER_COUNT];
//		int index = 0;
//		for (AtlasLayer l : layers) {
//			for (Byte b : l.getBytes()) {
//				bytes[index++] = b;
//			}
//		}
//		try {
//			FileOutputStream fos = new FileOutputStream(fileName + suffix);
//			fos.write(bytes);
//			fos.close();
//		} catch (IOException e) {
//			System.out.println("IOException : " + e);
//		}
//	}

//	public void loadFromFile(String fileName, String suffix) {
//		byte[] bytes = new byte[width * height * LAYER_COUNT];
//		try {
//			FileInputStream fis = new FileInputStream(fileName + suffix);
//			fis.read(bytes, 0, width * height * LAYER_COUNT);
//			fis.close();
//		} catch (IOException e) {
//			throw new TechnicalException("IOException : " + e);
//		}
//		int index = 0;
//		layers.clear();
//		for (int i = 0; i < LAYER_COUNT; i++) {
//			AtlasLayer l = new AtlasLayer(width, height);
//			for (int xy = 0; xy < width * height; xy++) {
//				l.setByte(xy, bytes[index++]);
//			}
//			layers.add(l);
//		}
//		buffers.clear();
//		buffers.add(buildBuffer(0));
//		buffers.add(buildBuffer(1));
//		toUpdate = true;
//	}

	public void updatePixel(int x, int y) {
		for (int i = 0; i < buffers.size(); i++) {
			int firstLayerIndex = i * 4;
			buffers.get(i).asIntBuffer().put(y * width + x, getBufferVal(x, y, firstLayerIndex));
		}
		toUpdate = true;
	}

	private int getBufferVal(int x, int y, int firstLayerIndex) {
		int r = (int) Math.round(layers.get(firstLayerIndex).get(x, y)) << 24;
		int g = (int) Math.round(layers.get(firstLayerIndex + 1).get(x, y)) << 16;
		int b = (int) Math.round(layers.get(firstLayerIndex + 2).get(x, y)) << 8;
		int a = (int) Math.round(layers.get(firstLayerIndex + 3).get(x, y));
		return (r + g + b + a);
	}

	@JsonIgnore
	public List<AtlasLayer> getLayers() {
		return layers;
	}

	@JsonIgnore
	public boolean isToUpdate() {
		return toUpdate;
	}

	@JsonIgnore
	public void setToUpdate(boolean toUpdate) {
		this.toUpdate = toUpdate;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
