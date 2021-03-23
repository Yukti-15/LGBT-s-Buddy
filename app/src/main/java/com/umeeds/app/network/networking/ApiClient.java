package com.umeeds.app.network.networking;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //  public static String BASE_URL = "http://clarigoinfotech.co.in/umeed/api/";
//    public static String BASE_URL = "https://umeed.app/api/";
    public static String BASE_URL = "https://umeed.app/app/api/";
    public static String Open311_Base_Url = "http://test311request.cityofchicago.org/open311/v2/";
    private static Retrofit retrofit = null;
    private final ApiInterface apiInterface;

    public ApiClient(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public static ApiInterface getInterface() {
        return getClient().create(ApiInterface.class);
    }

    private static Retrofit getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(15, TimeUnit.SECONDS);
        client.readTimeout(15, TimeUnit.SECONDS);
        client.writeTimeout(15, TimeUnit.SECONDS);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }


    private static OkHttpClient.Builder getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder();

    }

}
