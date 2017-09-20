package com.pgmail.martsulg.applicationforbalinasoft;

import android.util.Log;

import com.pgmail.martsulg.applicationforbalinasoft.base.BaseViewModel;
import com.pgmail.martsulg.domain.entity.PutPictureModel;
import com.pgmail.martsulg.domain.entity.ResponsePutPictureModel;
import com.pgmail.martsulg.domain.interactions.PutPictureUseCase;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by lenovo on 18.09.2017.
 */

public class CameraViewModel implements BaseViewModel {

    public String imageString;
    private long date;
    private double lat;
    private double lng;





    public CameraViewModel(String imageString, long date, double lat, double lng) {
        this.imageString = imageString;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public PutPictureUseCase putPictureUseCase = new PutPictureUseCase();

    @Override
    public void init() {

    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {

        Log.e("Otpraavliaem date: ", String.valueOf(date));
        Log.e("Otpraavliaem: lat: ", String.valueOf(lat));


        PutPictureModel putPictureModel = new PutPictureModel();
        putPictureModel.setImageString(imageString);
        putPictureModel.setDate(date);
        putPictureModel.setLat(lat);
        putPictureModel.setLng(lng);
        putPictureModel.setTOKEN(EntryActivity.preferences.getString("Token",null));

        putPictureUseCase.execute(putPictureModel, new DisposableObserver<ResponsePutPictureModel>() {
            @Override
            public void onNext(@NonNull ResponsePutPictureModel responsePutPictureModel) {

                long recieveDate = responsePutPictureModel.getDate();
                int recieveId = responsePutPictureModel.getId();
                double recieveLat = responsePutPictureModel.getLat();
                double recieveLng = responsePutPictureModel.getLng();
                String recieveUrl = responsePutPictureModel.getUrl();


                Log.e("recieveDate: ", String.valueOf(recieveDate));
                Log.e("recieveId: ", String.valueOf(recieveId));
                Log.e("recieveLat: ", String.valueOf(recieveLat));
                Log.e("recieveLng: ", String.valueOf(recieveLng));
                Log.e("recieveUrl: ", recieveUrl);





            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }



    @Override
    public void pause() {

    }
}
