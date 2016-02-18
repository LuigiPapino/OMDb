package net.dragora.omdb.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import net.dragora.omdb.BuildConfig;
import net.dragora.omdb.MyApplication;
import net.dragora.omdb.data.HistoryStore;
import net.dragora.omdb.data.providers.HistoryProvider;
import net.dragora.omdb.models.ResponseSearch;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private Context context;
    private PublishSubject<ResponseSearch> searchSubject = PublishSubject.create();

    public APIService apiService;
    public Retrofit retrofit;

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
     * @param keyword string
     * @return
     */
    public Observable<ResponseSearch> getAndFetchSearch(String keyword) {

        apiService.search(keyword, 1)
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    if(response.isSuccess()){
                        historyStore.put(keyword, response.body());
                    }else{
                        //TODO error subject
                    }
                });

        return  historyStore.getHistory()
                .filter(searchMap -> searchMap.containsKey(keyword))
                .map(searchMap -> searchMap.get(keyword));
    }
}
