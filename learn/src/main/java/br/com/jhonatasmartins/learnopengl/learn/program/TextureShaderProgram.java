package br.com.jhonatasmartins.learnopengl.learn.program;

import android.content.Context;
import android.util.Log;
import br.com.jhonatasmartins.learnopengl.learn.R;

import static android.opengl.GLES20.*;

/**
 * Created by jhonatas.santos on 10/29/2014.
 */
public class TextureShaderProgram extends ShaderProgram {

    // Uniform locations
    private final int matrixLocation;
    private final int textureUnitLocation;

    // Attribute locations
    private final int positionLocation;
    private final int textureCoordinatesLocation;

    public TextureShaderProgram(Context context){
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        // Retrieve uniform locations for the shader program.
        matrixLocation = glGetUniformLocation(program, MATRIX);
        textureUnitLocation = glGetUniformLocation(program, TEXTURE_UNIT);

        // Retrieve attribute locations for the shader program.
        positionLocation = glGetAttribLocation(program, POSITION);
        textureCoordinatesLocation = glGetAttribLocation(program, TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId){

        /* pass matrix to shader */
        glUniformMatrix4fv(matrixLocation, 1, false, matrix, 0);

        /* set active texture unit to 0 */
        glActiveTexture(GL_TEXTURE0);

        /* bind texture to unit */
        glBindTexture(GL_TEXTURE_2D, textureId);

        /* tell to texture to use it in shader read it from unit 0 */
        glUniform1i(textureUnitLocation, 0);
    }

    public int getPositionAttributeLocation() {
        return positionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return textureCoordinatesLocation;
    }
}
