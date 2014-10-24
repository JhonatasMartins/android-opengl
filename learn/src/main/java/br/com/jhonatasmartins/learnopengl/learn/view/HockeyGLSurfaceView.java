package br.com.jhonatasmartins.learnopengl.learn.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by jhonatas.santos on 10/22/2014.
 */
public class HockeyGLSurfaceView extends GLSurfaceView {

    final boolean rendered;

    public HockeyGLSurfaceView(Context context){
        super(context);

        /* create an open gl 2 context, only can called before setRenderer */
        setEGLContextClientVersion(2);

        /* set renderer for drawing GlSurface */
        setRenderer(new HockeyGLRenderer(context));

        rendered = true;
    }

    public boolean isRendered() {
        return rendered;
    }
}
