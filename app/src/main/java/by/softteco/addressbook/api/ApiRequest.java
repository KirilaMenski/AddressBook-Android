package by.softteco.addressbook.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kirila on 12.4.17.
 */

public class ApiRequest {

    static ApiRequest apiRequest;
    ApiInterface api;


    public static final String MAIN_URL = "https://maps.googleapis.com/maps/api/directions/";


    public static ApiRequest getInstance() {
        if (apiRequest == null) {
            apiRequest = new ApiRequest();
        }
        return apiRequest;
    }

    public ApiRequest() {
        Retrofit retrofit = getRetrofit();
        api = retrofit.create(ApiInterface.class);
    }

    public Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptor);
        client.readTimeout(5 * 60, TimeUnit.SECONDS);
        client.addInterceptor(new Interceptor() {
                                  @Override
                                  public okhttp3.Response intercept(Chain chain) throws IOException {
                                      Request original = chain.request();
                                      Request.Builder requestBuilder = original.newBuilder();
                                      requestBuilder.header("Content-Type", "application/json");
                                      Request request = requestBuilder.method(original.method(), original.body())
                                              .build();
                                      okhttp3.Response response = chain.proceed(request);
                                      return response;
                                  }
                              }
        );
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(SerializedName.class) == null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(MAIN_URL)
                .client(client.build())
                .build();
        return retrofit;
    }

    public ApiInterface getApi() {
        return api;
    }

}
