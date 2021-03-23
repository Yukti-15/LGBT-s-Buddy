package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBlockModel {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("fromblocked")
    @Expose
    private String fromblocked;


    @SerializedName("toblocked")
    @Expose
    private String toblocked;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getFromblocked() {
        return fromblocked;
    }

    public void setFromblocked(String fromblocked) {
        this.fromblocked = fromblocked;
    }

    public String getToblocked() {
        return toblocked;
    }

    public void setToblocked(String toblocked) {
        this.toblocked = toblocked;
    }
}
