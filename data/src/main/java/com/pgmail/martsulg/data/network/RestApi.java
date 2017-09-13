package com.pgmail.martsulg.data.network;

import com.pgmail.martsulg.data.entity.Profile;
import com.pgmail.martsulg.data.entity.ResponseAnswer;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lenovo on 10.09.2017.
 */

public interface RestApi {

    @POST("/api/account/signup")
    Observable<ResponseAnswer> createProfile(@Body Profile profile);

    @POST("/api/account/signin")
    Observable<ResponseAnswer> saveProfile(@Body Profile profile);

}
