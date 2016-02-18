package net.dragora.omdb.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import net.dragora.omdb.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by nietzsche on 18/02/16.
 */
public class NetworkApi {
    public static final String BASE_PATH_API = "http://www.omdbapi.com/";
    private static final String TAG = NetworkApi.class.getSimpleName();
    private Context context;

    public APIService apiService;
    public Retrofit retrofit;


    public NetworkApi(@NonNull Context context) {
        this.context = context.getApplicationContext();
        construct();

    }

    private void construct(){


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
}
