package com.example.shoppingapp.model;

import com.example.shoppingapp.Api.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClint {
    public static Retrofit retrofit;
    public static final String BASE_URL = "https://amazon24.p.rapidapi.com/api/";
    public static RetrofitClint instance;
    private RetrofitClint(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("x-rapidapi-key", "fd5a8df027msh0b94b28cfd9f50dp164841jsnf9573aea2221")
                                .header("x-rapidapi-host", "amazon24.p.rapidapi.com")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RetrofitClint getInstance(){
        if (retrofit == null){
            instance = new RetrofitClint();
            return  instance;
        }
        return instance;
    }
    public static Api getApi(){
        return retrofit.create(Api.class);
    }
}
