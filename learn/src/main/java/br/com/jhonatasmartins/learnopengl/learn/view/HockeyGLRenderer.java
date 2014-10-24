package br.com.jhonatasmartins.learnopengl.learn.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import br.com.jhonatasmartins.learnopengl.learn.R;
import br.com.jhonatasmartins.learnopengl.learn.helper.ShaderHelper;
import br.com.jhonatasmartins.learnopengl.learn.helper.TextReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by jhonatas.santos on 10/22/2014.
 */
public class HockeyGLRenderer implements GLSurfaceView.Renderer{

    final String LOG_TAG = "HockeyGLRenderer";
    final String COLOR = "u_Color";
    final String POSITION = "a_Position";
    final int POSITION_COMPONENT_COUNT = 2; //just x and y
    final int BYTES_PER_FLOAT = 4;
    final FloatBuffer vertexData;

    String vertexShader;
    String fragmentShader;
    int program;
    int colorLocation;
    int positionLocation;

    float[] tableVertices = {
            // Triangle 1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            // Triangle 2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            // Line 1
            -0.5f, 0f,
            0.5f, 0f,

            // Mallets
            0f, -0.25f,
            0f, 0.25f
    };

    public HockeyGLRenderer(Context context){
        /* allocate memory to object to jni knows how much memory opengl will use*/
        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
                               .order(ByteOrder.nativeOrder())
                               .asFloatBuffer();
        vertexData.put(tableVertices);

        /* load shaders vertex and fragment from resouces */
        vertexShader = TextReader.loadFromResource(context, R.raw.simple_vertex_shader);
        fragmentShader = TextReader.loadFromResource(context, R.raw.simple_fragment_shader);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        /* set background frame color */
        glClearColor(0, 0, 0, 0);

        int vertexId = ShaderHelper.compileVertexShader(vertexShader);
        int fragmentId = ShaderHelper.compileFragmentShader(fragmentShader);
        program = ShaderHelper.linkProgram(vertexId, fragmentId);

        boolean validate = ShaderHelper.validateProgram(program);
        if(!validate){
            Log.e(LOG_TAG, "")
        }


        /* use this program for draw anything to the screen*/
        glUseProgram(program);

        colorLocation = glGetUniformLocation(program, COLOR);
        positionLocation = glGetAttribLocation(program, POSITION);

        vertexData.position(0);
        glVertexAttribPointer(positionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);

        glEnableVertexAttribArray(positionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        /* set viewport size when surface changes */
       glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        /* redraw background color */
        glClear(GL_COLOR_BUFFER_BIT);


        /* draw hockey table */
        glUniform4f(colorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        /* draw hockey divider */
        glUniform4f(colorLocation, 0.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);

        /* draw the first mallet blue */
        glUniform4f(colorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

        /* draw the second mallet red */
        glUniform4f(colorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);


    }
}
