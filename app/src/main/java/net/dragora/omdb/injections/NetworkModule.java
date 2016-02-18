package net.dragora.omdb.injections;

import android.content.Context;

import net.dragora.omdb.data.HistoryStore;
import net.dragora.omdb.network.NetworkApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nietzsche on 18/02/16.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public NetworkApi provideNetworkApi(@ForApplication Context context, HistoryStore historyStore) {
        return new NetworkApi(context, historyStore);
    }
}
