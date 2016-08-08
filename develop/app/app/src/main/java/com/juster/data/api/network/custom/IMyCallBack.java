package com.juster.data.api.network.custom;

import java.io.IOException;
import  com.juster.data.api.network.IAPIResponseCode;
import retrofit.Response;

/**
 * Created by Anurag Singh on 15/12/15 1:38 PM.
 */
public interface IMyCallBack<T> {

    /** Called for {@link com.juster.data.api.network.IAPIResponseCode.SUCCESS} responses. */
    void success(Response<T> response);

    /** Called for {@link IAPIResponseCode.FAILURE} responses. */
    void serverFailure(Response<T> response);

    /** Called for {@link IAPIResponseCode.UNAUTHORIZED} responses. */
    void unauthenticated(Response<T> response);

    /** Called for {@link IAPIResponseCode.SERVER_DOWN} responses. */
    void serverDown(Response<T> response);

    /** Called for {@link IAPIResponseCode.USER_BLOCK} responses. */
    void userBlock(Response<T> response);

    /** Called for network errors while making the call. */
    void networkError(IOException e);

    /** Called for unexpected errors while making the call. */
    void unexpectedError(Throwable t);

}
