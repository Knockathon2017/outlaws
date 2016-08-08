package com.juster.data.api.network.custom;

import com.juster.data.api.network.IAPIConstants;
import com.juster.data.api.network.IAPIResponseCode;
import com.juster.logger.LoggerUtils;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MyCallAdapter<T> implements IMyCall<T> {

    public static String TAG = MyCallAdapter.class.getName();

    private final Call<T> call;
    private final Executor callbackExecutor;

    MyCallAdapter(Call<T> call, Executor callbackExecutor) {
        this.call = call;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public Response<T> execute() throws IOException {
        return call.execute();
    }

    @Override
    public void execute(IMyCallBack<T> callback) {

        try {
            Response<T> response = call.execute();
            if (response != null && response.isSuccess()) {
                String responseCode = response.headers().get(IAPIConstants.HEADER_RCODE);
                LoggerUtils.info(TAG, "execute::: responseCode=" + responseCode);

                try {
                    int rCode = Integer.parseInt(responseCode);
                    if (rCode == IAPIResponseCode.SUCCESS && response.body() != null) {
                        LoggerUtils.info(TAG, "execute::: success");
                        callback.success(response);
                    } else if (rCode == IAPIResponseCode.FAILURE && response.body() != null) {
                        LoggerUtils.info(TAG, "execute::: serverFailure");
                        callback.serverFailure(response);
                    } else if (rCode == IAPIResponseCode.UNAUTHORIZED && response.body() != null) {
                        LoggerUtils.info(TAG, "execute::: unauthenticated");
                        callback.unauthenticated(response);
                    } else if (rCode == IAPIResponseCode.SERVER_DOWN) {
                        LoggerUtils.info(TAG, "execute::: serverDown");
                        callback.serverDown(response);
                    }else {
                        LoggerUtils.info(TAG, "execute::: if else serverDown");
                        callback.serverDown(response);
                    }
                } catch (NumberFormatException e) {
                    LoggerUtils.info(TAG, "execute::: catch NumberFormatException serverDown");
                    callback.serverDown(response);
                }
            } else {
                LoggerUtils.info(TAG, "execute::: else serverDown");
                callback.serverDown(response);
            }
        } catch (IOException e) {
            callback.networkError(e);
        }
    }

    @Override
    public void enqueue(final IMyCallBack<T> callback) {

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(final Response<T> response, final Retrofit retrofit) {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null && response.isSuccess()) {
                            String responseCode = response.headers().get(IAPIConstants.HEADER_RCODE);
                            LoggerUtils.info(TAG, "execute::: responseCode=" + responseCode);
                            int rCode = Integer.parseInt(responseCode);
                            switch (rCode)
                            {
                                case IAPIResponseCode.UNAUTHORIZED:
                                    callback.unauthenticated(response);
                                    break;
                                case IAPIResponseCode.SUCCESS:
                                    callback.success(response);
                                    break;
                            }
                        } else {
                            LoggerUtils.info(TAG, "enqueue::: else serverDown");
                            callback.serverDown(response);
                        }
                    }
                });
            }

            @Override
            public void onFailure(final Throwable t) {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (t instanceof IOException) {
                            LoggerUtils.info(TAG, "onFailure::: networkError");
                            callback.networkError((IOException) t);
                        } else {
                            LoggerUtils.info(TAG, "onFailure::: unexpectedError");
                            callback.unexpectedError(t);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public IMyCall<T> clone() {
        return new MyCallAdapter<>(call.clone(), callbackExecutor);
    }

}
