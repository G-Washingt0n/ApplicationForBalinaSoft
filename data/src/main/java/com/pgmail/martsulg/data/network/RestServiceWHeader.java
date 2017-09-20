package com.pgmail.martsulg.data.network;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgmail.martsulg.data.entity.PutPicture;
import com.pgmail.martsulg.data.entity.ResponsePutPicture;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 19.09.2017.
 */

public class RestServiceWHeader {

    private static final RestServiceWHeader instance = new RestServiceWHeader();

    private RestApi restApi;
    private RestServiceWHeader() {
        init();
    }

    public static RestServiceWHeader getInstance() {
        return instance;
    }

    private void init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); //логи header + body



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    PutPicture putPicture = PutPicture.getInstance();
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Access-TokENZZO", putPicture.getTOKEN() )
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://213.184.248.43:9099/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build();

        restApi = retrofit.create(RestApi.class);
    }

    public Observable<ResponsePutPicture> sendPicture(PutPicture putPicture){
        Log.e("RestServiceWHeader req:", "AAAA");
        return restApi.sendPicture(putPicture);
    }
}
