package net.dragora.omdb.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import net.dragora.omdb.BuildConfig;
import net.dragora.omdb.MyApplication;
import net.dragora.omdb.data.HistoryStore;
import net.dragora.omdb.data.providers.HistoryProvider;
import net.dragora.omdb.models.ResponseSearch;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by nietzsche on 18/02/16.
 */
public class NetworkApi {
    public static final String BASE_PATH_API = "http://www.omdbapi.com/";
    private static final String TAG = NetworkApi.class.getSimpleName();
    public APIService apiService;
    public Retrofit retrofit;
    private Context context;
    private PublishSubject<String> searchErrorSubject = PublishSubject.create();
    private HistoryStore historyStore;


    public NetworkApi(@NonNull Context context, @NonNull HistoryStore historyStore) {

        this.context = context.getApplicationContext();
        this.historyStore = historyStore;
        construct();

    }

    private void construct() {


        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_PATH_API)
                .addConverterFactory(LoganSquareConverterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(APIService.class);
    }

    /**
     * Start a new fetch for the search and return the cached value if any.
     * Then obtain updates if the server return a successful response
     *
     * @param keyword string
     * @return
     */
    public Observable<ResponseSearch> getAndFetchSearch(String keyword) {

        apiService.search(keyword, 1)
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                            if (response.isSuccess() && response.body() != null && response.body().getResponse().compareToIgnoreCase("true") == 0) {
                                historyStore.put(keyword, response.body());
                            } else {
                                String error = "";
                                if (response.body() != null)
                                    error = response.body().getError();
                                else if (response.errorBody() != null)
                                    try {
                                        error = response.errorBody().string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                searchErrorSubject
                                        .onNext(error);
                            }
                        },
                        e -> {
                            searchErrorSubject.onNext(e.getMessage());
                        });

        return historyStore.getHistory()
                .filter(searchMap -> searchMap.containsKey(keyword))
                .map(searchMap -> searchMap.get(keyword));
    }

    public Observable<String> getSearchNetworkError() {
        return searchErrorSubject.asObservable();

    }

    @VisibleForTesting
    public okhttp3.mockwebserver.MockWebServer constructForTest() {


        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)

                .build();

        MockWebServer server = new MockWebServer();

        retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(LoganSquareConverterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(APIService.class);
        return server;
    }

}
