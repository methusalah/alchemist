package view.jme;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.jme3.effect.Particle;
import com.jme3.math.Matrix3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Format;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.util.BufferUtils;

public class MyParticlePointMesh extends MyParticleMesh{

    private MyParticleEmitter emitter;

    private int imagesX = 1;
    private int imagesY = 1;

    @Override
    public void setImagesXY(int imagesX, int imagesY) {
        this.imagesX = imagesX;
        this.imagesY = imagesY;
    }

    @Override
    public void initParticleData(MyParticleEmitter emitter, int numParticles) {
        setMode(Mode.Points);

        this.emitter = emitter;

        // set positions
        FloatBuffer pb = BufferUtils.createVector3Buffer(numParticles);
        
        //if the buffer is already set only update the data
        VertexBuffer buf = getBuffer(VertexBuffer.Type.Position);
        if (buf != null) {
            buf.updateData(pb);
        } else {
            VertexBuffer pvb = new VertexBuffer(VertexBuffer.Type.Position);
            pvb.setupData(Usage.Stream, 3, Format.Float, pb);
            setBuffer(pvb);
        }

        // set colors
        ByteBuffer cb = BufferUtils.createByteBuffer(numParticles * 4);
        
        buf = getBuffer(VertexBuffer.Type.Color);
        if (buf != null) {
            buf.updateData(cb);
        } else {
            VertexBuffer cvb = new VertexBuffer(VertexBuffer.Type.Color);
            cvb.setupData(Usage.Stream, 4, Format.UnsignedByte, cb);
            cvb.setNormalized(true);
            setBuffer(cvb);
        }

        // set sizes
        FloatBuffer sb = BufferUtils.createFloatBuffer(numParticles);
        
        buf = getBuffer(VertexBuffer.Type.Size);
        if (buf != null) {
            buf.updateData(sb);
        } else {
            VertexBuffer svb = new VertexBuffer(VertexBuffer.Type.Size);
            svb.setupData(Usage.Stream, 1, Format.Float, sb);
            setBuffer(svb);
        }

        // set UV-scale
        FloatBuffer tb = BufferUtils.createFloatBuffer(numParticles*4);
        
        buf = getBuffer(VertexBuffer.Type.TexCoord);
        if (buf != null) {
            buf.updateData(tb);
        } else {
            VertexBuffer tvb = new VertexBuffer(VertexBuffer.Type.TexCoord);
            tvb.setupData(Usage.Stream, 4, Format.Float, tb);
            setBuffer(tvb);
        }
        
        updateCounts();
    }

    @Override
    public void updateParticleData(Particle[] particles, Camera cam, Matrix3f inverseRotation) {
        VertexBuffer pvb = getBuffer(VertexBuffer.Type.Position);
        FloatBuffer positions = (FloatBuffer) pvb.getData();

        VertexBuffer cvb = getBuffer(VertexBuffer.Type.Color);
        ByteBuffer colors = (ByteBuffer) cvb.getData();

        VertexBuffer svb = getBuffer(VertexBuffer.Type.Size);
        FloatBuffer sizes = (FloatBuffer) svb.getData();

        VertexBuffer tvb = getBuffer(VertexBuffer.Type.TexCoord);
        FloatBuffer texcoords = (FloatBuffer) tvb.getData();

        float sizeScale = emitter.getWorldScale().x;

        // update data in vertex buffers
        positions.rewind();
        colors.rewind();
        sizes.rewind();
        texcoords.rewind();
        for (int i = 0; i < particles.length; i++){
            Particle p = particles[i];
            
            positions.put(p.position.x)
                     .put(p.position.y)
                     .put(p.position.z);

            sizes.put(p.size * sizeScale);
            colors.putInt(p.color.asIntABGR());

            int imgX = p.imageIndex % imagesX;
            int imgY = (p.imageIndex - imgX) / imagesY;

            float startX = ((float) imgX) / imagesX;
            float startY = ((float) imgY) / imagesY;
            float endX   = startX + (1f / imagesX);
            float endY   = startY + (1f / imagesY);

            texcoords.put(startX).put(startY).put(endX).put(endY);
        }
        positions.flip();
        colors.flip();
        sizes.flip();
        texcoords.flip();

        // force renderer to re-send data to GPU
        pvb.updateData(positions);
        cvb.updateData(colors);
        svb.updateData(sizes);
        tvb.updateData(texcoords);
    }
}
