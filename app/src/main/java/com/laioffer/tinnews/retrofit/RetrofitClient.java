package com.laioffer.tinnews.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //replace it with your API key
    private static final String API = "ca4c605496d74adca2889426a8ccdfef";
    //base url
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit instance;

    //singleton constructor
    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // convert jason data to observable:source of data
                    .addConverterFactory(GsonConverterFactory.create()) // parse json data
                    .client(getHttpClient())
                    .build();
        }
        return instance;
    }


    private static OkHttpClient getHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new NewsInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        return builder.build();
    }

    private static class NewsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("X-Api-Key", API)
                    .build();

            return chain.proceed(request);
        }
    }
}

