package br.com.jhonatasmartins.learnopengl.learn;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;
import br.com.jhonatasmartins.learnopengl.learn.helper.TextReader;
import br.com.jhonatasmartins.learnopengl.learn.view.HockeyGLSurfaceView;

public class HockeyActivity extends Activity {

    HockeyGLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new HockeyGLSurfaceView(this);

        setContentView(glSurfaceView);

        logSupportedOpenGLVersion();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* surface must be notified when activity is resumed */
        if(glSurfaceView.isRendered())
            glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        /* surface must be notified when activity is paused */
        if(glSurfaceView.isRendered())
            glSurfaceView.onPause();
    }

    private void logSupportedOpenGLVersion(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configInfo = activityManager.getDeviceConfigurationInfo();

        Log.d("MainActivity", "supported opengl version : " + configInfo.getGlEsVersion());
    }
}
