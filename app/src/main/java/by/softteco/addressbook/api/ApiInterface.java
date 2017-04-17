package by.softteco.addressbook.api;

import by.softteco.addressbook.api.response.ResponseObject;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kirila on 12.4.17.
 */

public interface ApiInterface {

    @GET("json?")
    Observable<ResponseObject> getPath(@Query("origin") String originName, @Query("destination") String destination, @Query("key") String apiKey);

}
