package com.pgmail.martsulg.domain.interactions;

import com.pgmail.martsulg.data.entity.Profile;
import com.pgmail.martsulg.data.entity.ResponseAnswer;
import com.pgmail.martsulg.data.network.RestService;
import com.pgmail.martsulg.domain.entity.ProfileModel;
import com.pgmail.martsulg.domain.entity.ResponseAnswerModel;
import com.pgmail.martsulg.domain.entity.ResponseProfileModel;
import com.pgmail.martsulg.domain.interactions.base.UseCase;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;



public class SaveProfileUseCase extends UseCase<ProfileModel,ResponseAnswerModel> {




    @Override
    protected Observable<ResponseAnswerModel> buildUseCase(ProfileModel param) {

        Profile profile = new Profile();

        profile.setLogin(param.getLogin());
        profile.setPassword(param.getPassword());

        return RestService.getInstance().saveProfile(profile).map(new Function<ResponseAnswer, ResponseAnswerModel>() {
            @Override
            public ResponseAnswerModel apply(@NonNull ResponseAnswer responseAnswer) throws Exception {
                return convert(responseAnswer);
            }
        });
    }


    private ResponseAnswerModel convert(ResponseAnswer responseAnswer){

        ResponseAnswerModel responseAnswerModel = new ResponseAnswerModel();
        responseAnswerModel.setStatus(responseAnswer.getStatus());

        ResponseProfileModel responseProfileModel = new ResponseProfileModel();
        responseProfileModel.setLogin(responseAnswer.getResponseProfile().getLogin());
        responseProfileModel.setToken(responseAnswer.getResponseProfile().getToken());
        responseProfileModel.setUserId(responseAnswer.getResponseProfile().getUserId());

        responseAnswerModel.setResponseProfileModel(responseProfileModel);
        return responseAnswerModel;
    }
}
