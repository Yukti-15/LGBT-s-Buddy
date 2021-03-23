package com.umeeds.app.network.basenetwork;

public interface ServerResponseListener {

    //apart from api call error, server communication errors
    void serverError(String stringFromByte, int paramInt, int errorCode);

    //api call failure response
    void onFailure(Throwable t, int requestId);
}
