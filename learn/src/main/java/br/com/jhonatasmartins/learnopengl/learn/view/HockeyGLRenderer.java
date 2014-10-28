package br.com.jhonatasmartins.learnopengl.learn.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import br.com.jhonatasmartins.learnopengl.learn.R;
import br.com.jhonatasmartins.learnopengl.learn.helper.ShaderHelper;
import br.com.jhonatasmartins.learnopengl.learn.helper.TextReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
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
    final String COLOR = "a_Color";
    final String POSITION = "a_Position";
    final String MATRIX = "u_Matrix";
    final int POSITION_COMPONENT_COUNT = 4; // x,y,z,w
    final int COLOR_COMPONENT_COUNT = 3;  // r g b
    final int BYTES_PER_FLOAT = 4;
    final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    final float[] projectionMatrix = new float[16];
    final float[] modelMatrix = new float[16];
    final FloatBuffer vertexData;

    String vertexShader;
    String fragmentShader;
    int program;
    int colorLocation;
    int positionLocation;
    int matrixLocation;


    float[] tableVertices = {
            /* X, Y, Z, W, R, G, B */

            // Triangle Fan
            0f, 0f, 0f, 1.5f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,

            // Line 1
            -0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,
            0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,

            // Mallets
            0f, -0.4f, 0f, 1.25f, 0f, 0f, 1f,
            0f, 0.4f, 0f, 1.75f, 1f, 0f, 0f
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

        ShaderHelper.validateProgram(program);

        /* use this program for draw anything to the screen*/
        glUseProgram(program);

        colorLocation = glGetAttribLocation(program, COLOR);
        positionLocation = glGetAttribLocation(program, POSITION);
        matrixLocation = glGetUniformLocation(program, MATRIX);

        vertexData.position(0);
        glVertexAttribPointer(positionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);

        glEnableVertexAttribArray(positionLocation);

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(colorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);

        glEnableVertexAttribArray(colorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        /* set viewport size when surface changes */
       glViewport(0, 0, width, height);

       float aspectRatio = (width > height) ? width / height : height / width;

        Matrix.perspectiveM(projectionMatrix, 0, 35, aspectRatio, 1f, 10f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        /* redraw background color */
        glClear(GL_COLOR_BUFFER_BIT);

        /* send orthographic projection to shader */
        glUniformMatrix4fv(matrixLocation, 1, false, projectionMatrix, 0);

        /* draw hockey table */
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        /* draw hockey divider */
        glDrawArrays(GL_LINES, 6, 2);

        /* draw the first mallet blue */
        glDrawArrays(GL_POINTS, 8, 1);

        /* draw the second mallet red */
        glDrawArrays(GL_POINTS, 9, 1);

    }
}
