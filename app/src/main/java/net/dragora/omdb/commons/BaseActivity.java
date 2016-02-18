package net.dragora.omdb.commons;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by nietzsche on 28/01/16.
 */
public class BaseActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private static final boolean showLog = Utility.showLog;


    protected void logD(String string) {
        if (showLog)
            Log.d(TAG, string);
    }
}
