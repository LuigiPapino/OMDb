package net.dragora.omdb.injections;

import android.content.Context;

import net.dragora.omdb.data.HistoryStore;
import net.dragora.omdb.data.HistoryStoreFromProvider;
import net.dragora.omdb.network.NetworkApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nietzsche on 18/02/16.
 */
@Module
public class DataStoreModule {

    @Provides
    @Singleton
    public HistoryStore provideHistoryStore(@ForApplication Context context) {
        return new HistoryStoreFromProvider(context);
    }
}
