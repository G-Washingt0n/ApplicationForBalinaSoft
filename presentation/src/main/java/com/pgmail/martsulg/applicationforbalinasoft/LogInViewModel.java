package com.pgmail.martsulg.applicationforbalinasoft;


import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.pgmail.martsulg.applicationforbalinasoft.base.BaseViewModel;
import com.pgmail.martsulg.domain.entity.ProfileModel;
import com.pgmail.martsulg.domain.entity.ResponseAnswerModel;
import com.pgmail.martsulg.domain.interactions.SaveProfileUseCase;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class LogInViewModel implements BaseViewModel {

    public ObservableField<String> login2send= new ObservableField<>();
    public ObservableField<String> password2send = new ObservableField<>();
    private int status;
    private String login;
    private int id;
    private String token;

    FragmentActivity activity;
    Intent intent;

    public LogInViewModel(FragmentActivity activity) {
        this.activity = activity;
        intent = new Intent(activity, ContentActivity.class);
    }

    public SaveProfileUseCase saveProfileUseCase = new SaveProfileUseCase();


    @Override
    public void init() {

    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {



    }


    public void onSignInClick(){
        //try{
        Log.e("Otpraavliaem: ", login2send.get());
        Log.e("Otpraavliaem: ", password2send.get());

        ProfileModel profileModel = new ProfileModel();
        profileModel.setLogin(login2send.get());
        profileModel.setPassword(password2send.get());

            saveProfileUseCase.execute(profileModel, new DisposableObserver<ResponseAnswerModel>(){


                @Override
                public void onNext(@NonNull ResponseAnswerModel responseAnswerModel) {
                    status = responseAnswerModel.getStatus();
                    login = responseAnswerModel.getResponseProfileModel().getLogin();
                    id = responseAnswerModel.getResponseProfileModel().getUserId();
                    token = responseAnswerModel.getResponseProfileModel().getToken();

                    Log.e("Otvet status: ", String.valueOf(status));
                    Log.e("Otvet login: ", login);
                    Log.e("Otvet id: ", String.valueOf(id));
                    Log.e("Otvet token: ", token);

                    intent.putExtra("Token",token);
                    intent.putExtra("Login", login);
                    activity.startActivity(intent);


                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
/*} catch (Exception e){
    Log.e("Error", e.toString());
}*/



    }


    @Override
    public void pause() {
        saveProfileUseCase.dispose();
    }


}
