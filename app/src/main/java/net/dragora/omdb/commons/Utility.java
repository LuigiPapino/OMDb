package net.dragora.omdb.commons;

import android.util.Log;

import net.dragora.omdb.BuildConfig;


/**
 * Created by nietzsche on 16/02/16.
 */
public class Utility {

    public static boolean showLog = BuildConfig.DEBUG;

    public static void logD(String TAG, String message){
        if (showLog)
            Log.d(TAG, message);
    }
}
