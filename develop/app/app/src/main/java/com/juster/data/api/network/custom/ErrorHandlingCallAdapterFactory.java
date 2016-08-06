package com.juster.data.api.network.custom;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit.Call;
import retrofit.CallAdapter;
import retrofit.Retrofit;

/**
 * Created by Anurag Singh on 15/12/15 1:12 PM.
 */
public class ErrorHandlingCallAdapterFactory implements CallAdapter.Factory{
    /**
     * Returns a call adapter for interface methods that return {@code returnType}, or null if this
     * factory doesn't adapt that type.
     *
     * @param returnType
     * @param annotations
     * @param retrofit
     */
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        TypeToken<?> token = TypeToken.get(returnType);
        if (token.getRawType() != IMyCall.class) {
            return null;
        }

        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "IMyCall must have generic type (e.g., IMyCall<ResponseBody>)");
        }

        final Type responseType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
        return new CallAdapter<IMyCall<?>>() {
            @Override public Type responseType() {
                return responseType;
            }

            @Override public <R> IMyCall<R> adapt(Call<R> call) {
                return new MyCallAdapter<>(call, new MainThreadExecutor());
            }
        };
    }

    static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());


        @Override public void execute(Runnable r) {
            handler.post(r);
        }
    }
}
