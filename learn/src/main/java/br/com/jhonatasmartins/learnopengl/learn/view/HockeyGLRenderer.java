package br.com.jhonatasmartins.learnopengl.learn.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import br.com.jhonatasmartins.learnopengl.learn.R;
import br.com.jhonatasmartins.learnopengl.learn.helper.ShaderHelper;
import br.com.jhonatasmartins.learnopengl.learn.helper.TextReader;
import br.com.jhonatasmartins.learnopengl.learn.helper.TextureHelper;
import br.com.jhonatasmartins.learnopengl.learn.objects.Mallet;
import br.com.jhonatasmartins.learnopengl.learn.objects.Table;
import br.com.jhonatasmartins.learnopengl.learn.program.ColorShaderProgram;
import br.com.jhonatasmartins.learnopengl.learn.program.TextureShaderProgram;

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

    final float[] projectionMatrix = new float[16];
    final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;

    private int texture;

    private Context context;

    public HockeyGLRenderer(Context context){
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet();

        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey);
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
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        // Draw the table.
        textureShaderProgram.useProgram();
        textureShaderProgram.setUniforms(projectionMatrix, texture);
        table.bindData(textureShaderProgram);
        table.onDraw();

        // Draw the mallets.
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(projectionMatrix);
        mallet.bindData(colorShaderProgram);
        mallet.onDraw();
    }
}
