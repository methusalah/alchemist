package view.jme;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;

public class CenteredQuad extends Mesh {
	private float width;
	private float height;

	/**
	 * Serialization only. Do not use.
	 */
	public CenteredQuad() {
	}

	/**
	 * Create a quad with the given width and height. The quad is always created
	 * in the XY plane.
	 * 
	 * @param width
	 *            The X extent or width
	 * @param height
	 *            The Y extent or height
	 */
	public CenteredQuad(float width, float height) {
		updateGeometry(width, height);
	}

	/**
	 * Create a quad with the given width and height. The quad is always created
	 * in the XY plane.
	 * 
	 * @param width
	 *            The X extent or width
	 * @param height
	 *            The Y extent or width
	 * @param flipCoords
	 *            If true, the texture coordinates will be flipped along the Y
	 *            axis.
	 */
	public CenteredQuad(float width, float height, boolean flipCoords) {
		updateGeometry(width, height, flipCoords);
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public void updateGeometry(float width, float height) {
		updateGeometry(width, height, false);
	}

	public void updateGeometry(float width, float height, boolean flipCoords) {
		this.width = width;
		this.height = height;
		float halfWidth = width/2;
		float halfHeight = height/2;
		setBuffer(Type.Position, 3, new float[] {
				-halfWidth, -halfHeight, 0,
				halfWidth, -halfHeight, 0,
				halfWidth, halfHeight, 0,
				-halfWidth, halfHeight, 0 });

		if (flipCoords) {
			setBuffer(Type.TexCoord, 2, new float[] { 0, 1, 1, 1, 1, 0, 0, 0 });
		} else {
			setBuffer(Type.TexCoord, 2, new float[] { 0, 0, 1, 0, 1, 1, 0, 1 });
		}
		setBuffer(Type.Normal, 3, new float[] { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 });
		if (height < 0) {
			setBuffer(Type.Index, 3, new short[] { 0, 2, 1, 0, 3, 2 });
		} else {
			setBuffer(Type.Index, 3, new short[] { 0, 1, 2, 0, 2, 3 });
		}

		updateBound();
	}
}
