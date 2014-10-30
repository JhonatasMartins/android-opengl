package br.com.jhonatasmartins.learnopengl.learn.objects;

import android.opengl.GLES20;
import br.com.jhonatasmartins.learnopengl.learn.data.VertexArray;
import br.com.jhonatasmartins.learnopengl.learn.program.ShaderProgram;
import br.com.jhonatasmartins.learnopengl.learn.program.TextureShaderProgram;

/**
 * Created by jhonatas.santos on 10/29/2014.
 */
public class Table extends VertexArray {

    final int POSITION_COMPONENT_COUNT = 2; // x,y
    final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, S, T
            // Triangle Fan
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };

    public Table(){
        super(VERTEX_DATA);
    }

    @Override
    public void bindData(ShaderProgram shaderProgram) {
        TextureShaderProgram textureShaderProgram  = (TextureShaderProgram)shaderProgram;

        setVertexAttribPointer(
                0,
                textureShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureShaderProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    @Override
    public void onDraw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}
