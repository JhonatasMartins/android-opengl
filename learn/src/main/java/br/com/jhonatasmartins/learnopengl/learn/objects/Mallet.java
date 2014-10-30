package br.com.jhonatasmartins.learnopengl.learn.objects;

import android.opengl.GLES20;
import br.com.jhonatasmartins.learnopengl.learn.data.VertexArray;
import br.com.jhonatasmartins.learnopengl.learn.program.ColorShaderProgram;
import br.com.jhonatasmartins.learnopengl.learn.program.ShaderProgram;

/**
 * Created by jhonatas.santos on 10/29/2014.
 */
public class Mallet extends VertexArray {

    private final int POSITION_COMPONENT_COUNT = 2; //x, y
    private final int COLOR_COMPONENT_COUNT = 3;
    private final int STRIDE =(POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, R, G, B
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    };

    public Mallet(){
        super(VERTEX_DATA);
    }

    @Override
    public void bindData(ShaderProgram shaderProgram) {
        ColorShaderProgram colorProgram = (ColorShaderProgram) shaderProgram;

        setVertexAttribPointer(
                0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                colorProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);
    }

    @Override
    public void onDraw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2);
    }
}
