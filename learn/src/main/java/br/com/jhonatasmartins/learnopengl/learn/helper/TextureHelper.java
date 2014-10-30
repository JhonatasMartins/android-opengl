package br.com.jhonatasmartins.learnopengl.learn.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by jhonatas.santos on 10/28/2014.
 */
public final class TextureHelper {

    private final static String LOG_TAG = "TextureHelper";

    private TextureHelper(){}

    public static int loadTexture(Context context, int resourceId){

        final int[] textureObjectIds = new int[1];
        glGenTextures(1, textureObjectIds, 0);

        if(textureObjectIds[0] == 0){
            Log.d(LOG_TAG, "cannot create new texture object");

            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        if(bitmap == null){
            Log.d(LOG_TAG, "cannot decode image " + resourceId);

            glDeleteTextures(1, textureObjectIds, 0);
        }

        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);

        return textureObjectIds[0];
    }
}
