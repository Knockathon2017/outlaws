package com.juster.data.api.network;
public interface IAPIResponseCode {

    int SUCCESS = 100;
    int FAILURE = 101;
    int UNAUTHORIZED = 102;
    int SERVER_DOWN = 103;

    //Internal use
    int HTTP_CLIENT_ERROR_NOT_FOUND = 404;
}
