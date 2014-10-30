package br.com.jhonatasmartins.learnopengl.learn.program;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import br.com.jhonatasmartins.learnopengl.learn.helper.ShaderHelper;
import br.com.jhonatasmartins.learnopengl.learn.helper.TextReader;

/**
 * Created by jhonatas.santos on 10/29/2014.
 */
public abstract class ShaderProgram {

    // Uniform constants
    protected static final String MATRIX = "u_Matrix";
    protected static final String TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    protected static final String POSITION = "a_Position";
    protected static final String COLOR = "a_Color";
    protected static final String TEXTURE_COORDINATES = "a_TextureCoordinates";

    // Shader program
    protected final int program;

    public ShaderProgram(Context context, int vertexResourceId, int fragmentResourceId){
        /* compile shaders and build a program */
        program = ShaderHelper.buildProgram(TextReader.loadFromResource(context, vertexResourceId),
                                            TextReader.loadFromResource(context, fragmentResourceId));
    }

    public void useProgram(){
        GLES20.glUseProgram(program);
    }

}
