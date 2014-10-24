package br.com.jhonatasmartins.learnopengl.learn.helper;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by jhonatas.santos on 10/22/2014.
 */
public final class TextReader {

    private final static String LOG_TAG = "TextReader";

    private TextReader(){}

    /**
     * Get string from resource file
     * @param context Context
     * @param resourceId Resource R.raw.file
     * @return
     */
    public static String loadFromResource(Context context, int resourceId){

        InputStream in = context.getResources().openRawResource(resourceId);
        String text = new Scanner(in).useDelimiter("\\A").next();

        try {
            in.close();
        }catch (IOException e){
            Log.e(LOG_TAG, e.getMessage());
        }

        return text;
    }


}
