package com.pgmail.martsulg.domain.interactions;

import com.pgmail.martsulg.data.entity.PutPicture;
import com.pgmail.martsulg.data.entity.ResponsePutPicture;
import com.pgmail.martsulg.data.network.RestServiceWHeader;
import com.pgmail.martsulg.domain.entity.PutPictureModel;
import com.pgmail.martsulg.domain.entity.ResponsePutPictureModel;
import com.pgmail.martsulg.domain.interactions.base.UseCase;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class PutPictureUseCase extends UseCase<PutPictureModel,ResponsePutPictureModel> {


    @Override
    protected Observable<ResponsePutPictureModel> buildUseCase(PutPictureModel param) {

        PutPicture putPicture= new PutPicture();

        putPicture.setImageString(param.getImageString());
        putPicture.setDate(param.getDate());
        putPicture.setLat(param.getLat());
        putPicture.setLng(param.getLng());
        putPicture.setTOKEN(param.getTOKEN());


        return RestServiceWHeader.getInstance().sendPicture(putPicture).map(new Function<ResponsePutPicture, ResponsePutPictureModel>() {
            @Override
            public ResponsePutPictureModel apply(@NonNull ResponsePutPicture responsePutPicture) throws Exception {
                return convert(responsePutPicture);
            }
        });
    }


    private ResponsePutPictureModel convert(ResponsePutPicture responsePutPicture){

        ResponsePutPictureModel responsePutPictureModel = new ResponsePutPictureModel();
        responsePutPictureModel.setDate(responsePutPicture.getDate());
        responsePutPictureModel.setId(responsePutPicture.getId());
        responsePutPictureModel.setLat(responsePutPicture.getLat());
        responsePutPictureModel.setLng(responsePutPicture.getLng());
        responsePutPictureModel.setUrl(responsePutPicture.getUrl());

        return responsePutPictureModel;
    }
}
