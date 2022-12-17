package com.example.bhakamusic.Apis;

import com.example.bhakamusic.configs.Configs;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;
    public static String token="1";

    public void setToken(String token){
        RetrofitClient.token = token;
    }

    private RetrofitClient(){
        if(Objects.equals(token, "1")){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", token).build();
                    return chain.proceed(request);
                }
            });
             retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Configs.BASE_URL)
                    .client(httpClient.build())
                    .build();
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(Configs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
