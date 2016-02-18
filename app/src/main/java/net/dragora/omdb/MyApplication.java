package net.dragora.omdb;

import android.app.Application;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import net.dragora.omdb.injections.Graph;

/**
 * Created by nietzsche on 18/02/16.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication instance;

    private Graph graph;
    private RefWatcher refWatcher;

    @NonNull
    public static MyApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        instance = this;
        refWatcher = LeakCanary.install(this);
        graph = Graph.Initializer.init(this);
        graph.inject(this);
        super.onCreate();

    }


    public Graph getGraph() {
        return graph;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}

