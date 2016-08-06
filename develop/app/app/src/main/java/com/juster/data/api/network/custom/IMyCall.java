package com.juster.data.api.network.custom;

import java.io.IOException;
import retrofit.Response;

/**
 * Created by Anurag Singh on 15/12/15 1:29 PM.
 */
public interface IMyCall<T> extends Cloneable {
    Response<T> execute() throws IOException;
    void execute(IMyCallBack<T> callback);
    void enqueue(IMyCallBack<T> callback);
    void cancel();
    IMyCall<T> clone();
}
