package br.com.jhonatasmartins.learnopengl.learn.program;

import android.content.Context;
import android.util.Log;
import br.com.jhonatasmartins.learnopengl.learn.R;

import static android.opengl.GLES20.*;

/**
 * Created by jhonatas.santos on 10/29/2014.
 */
public class ColorShaderProgram extends ShaderProgram {

    // Uniform locations
    private final int matrixLocation;

    // Attribute locations
    private final int positionLocation;
    private final int colorLocation;


    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        /* get location for the shader program */
        matrixLocation = glGetUniformLocation(program, MATRIX);

        /* get attribute location for the shader program */
        positionLocation = glGetAttribLocation(program, POSITION);
        colorLocation = glGetAttribLocation(program, COLOR);
    }

    public void setUniforms(float[] matrix) {
         /* pass matrix to shader */
        glUniformMatrix4fv(matrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return positionLocation;
    }
    public int getColorAttributeLocation() {
        return colorLocation;
    }
}
