package br.com.jhonatasmartins.learnopengl.learn.data;

import br.com.jhonatasmartins.learnopengl.learn.program.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by jhonatas.santos on 10/29/2014.
 */
public abstract class VertexArray {

    protected final static int BYTES_PER_FLOAT = 4;
    final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData){
        /* allocate memory to object to jni knows how much memory opengl will use*/
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                                .order(ByteOrder.nativeOrder())
                                .asFloatBuffer()
                                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
                                       int componentCount, int stride) {

        floatBuffer.position(dataOffset);

        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT,
                false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }

    public abstract void bindData( ShaderProgram shaderProgram );

    public abstract void onDraw();

}
