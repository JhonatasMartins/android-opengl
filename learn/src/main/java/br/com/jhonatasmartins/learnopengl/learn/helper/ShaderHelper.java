package br.com.jhonatasmartins.learnopengl.learn.helper;

import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by jhonatas.santos on 10/22/2014.
 */
public final class ShaderHelper {

    private final static String LOG_TAG = "ShaderHelper";

    private ShaderHelper(){}

    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int[] linkStatus = new int[1];
        int programIdResult = 0;

        /* create new program object */
        int programId = glCreateProgram();

        if(programId == 0){
            Log.d(LOG_TAG, "cannot create new program object");

            return 0;
        }

        programIdResult = programId;

        /* attach our shaders to program */
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);

        /* join our shaders */
        glLinkProgram(programId);

        /* check if link failed or succeeded*/
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);

        /* if link program failed */
        if(linkStatus[0] == 0){
            /* delete program */
            glDeleteProgram(programId);

            /* assign result to 0 */
            programIdResult = 0;
        }

         /* get human readable message of that happened while compile shader */
        Log.e(LOG_TAG, "link program : " + glGetProgramInfoLog(programId));

        return programIdResult;
    }

    private static int compileShader(int type, String shaderCode){
        final int[] compileStatus = new int[1];
        int shaderIdResult = 0;

        /* create new shader object */
        final int shaderId = glCreateShader(type);

        if(shaderId == 0){
            Log.d(LOG_TAG, "cannot create new shader object");

            return 0;
        }

        shaderIdResult = shaderId;

        /* upload shader, read source code defined in the String shaderCode */
        glShaderSource(shaderId, shaderCode);

        /* compile shader, this compile shader that was uploaded using glShaderSource  */
        glCompileShader(shaderId);

        /* get compilation status */
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0);

        /* if compile status failed */
        if(compileStatus[0] == 0){
            /* delete shader object */
            glDeleteShader(shaderId);

            /* assign result shader to 0 */
            shaderIdResult = 0;
        }

        /* get human readable message of that happened while compile shader */
        Log.e(LOG_TAG, "shader compile : " + glGetShaderInfoLog(shaderId));

        return shaderIdResult;
    }

    public static int compileVertexShader(String shaderCode){
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    public static boolean validateProgram(int programId){
        final int[] validateStatus = new int[1];

        glValidateProgram(programId);

        glGetProgramiv(programId, GL_VALIDATE_STATUS, validateStatus, 0);

         /* get human readable message of that happened while compile shader */
        Log.e(LOG_TAG, "validate status program : " + glGetProgramInfoLog(programId));

        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource){

        int program;

        /* compile sources shaders */
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        /* link shaders in one program */
        program = linkProgram(vertexShader, fragmentShader);

        validateProgram(program);

        return program;
    }
}
