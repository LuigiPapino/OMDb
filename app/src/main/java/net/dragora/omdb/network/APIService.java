package net.dragora.omdb.network;

import net.dragora.omdb.models.ResponseSearch;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nietzsche on 18/02/16.
 */
public interface APIService {

    @GET("?r=json&type=movie")
    Observable<Response<ResponseSearch>> search(@Query("s") String search, @Query("page") int page);
}
