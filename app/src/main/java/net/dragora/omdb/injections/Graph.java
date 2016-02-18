package net.dragora.omdb.injections;

import android.app.Application;

import net.dragora.omdb.MyApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nietzsche on 18/02/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class, DataStoreModule.class, NetworkModule.class})
public interface Graph {




    void inject(MyApplication myApplication);

    final class Initializer {

        public static Graph init(Application application) {
            return DaggerGraph.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }

    }
}