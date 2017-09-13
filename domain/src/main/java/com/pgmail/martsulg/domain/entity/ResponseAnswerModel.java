package com.pgmail.martsulg.domain.entity;

public class ResponseAnswerModel {

    private int status;

    private ResponseProfileModel responseProfileModel;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResponseProfileModel getResponseProfileModel() {
        return responseProfileModel;
    }

    public void setResponseProfileModel(ResponseProfileModel responseProfileModel) {
        this.responseProfileModel = responseProfileModel;
    }
}
