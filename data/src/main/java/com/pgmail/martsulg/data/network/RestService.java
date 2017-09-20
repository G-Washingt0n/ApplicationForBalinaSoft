package com.pgmail.martsulg.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgmail.martsulg.data.entity.Profile;
import com.pgmail.martsulg.data.entity.ResponseAnswer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestService {



    private static final RestService instance = new RestService();

    private RestApi restApi;
    private RestService() {
        init();
    }

    public static RestService getInstance() {
        return instance;
    }

    private void init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); //логи header + body



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://213.184.248.43:9099/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();

        restApi = retrofit.create(RestApi.class);
    }

    public Observable<ResponseAnswer> createProfile(Profile profile) {
        return restApi.createProfile(profile);
    }

    public Observable<ResponseAnswer> saveProfile(Profile profile) {
        return restApi.saveProfile(profile);
    }
}
