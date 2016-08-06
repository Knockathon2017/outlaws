package com.juster.data.api.network.custom;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.juster.data.api.network.IAPIAdjusterMate;
import com.juster.data.api.network.IAPIConstants;
import com.juster.data.api.network.IAPIUrlBaseConstants;
import com.juster.logger.LoggerUtils;
import com.juster.prefs.PreferenceManagerSingleton;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by deepakj on 4/8/16.
 */

/**
 * Entry point for all requests to **justerServiceClient** API.
 * Uses Retrofit library to abstract the actual REST API into a service.
 */

public class JusterLoginServiceClient {

    public static String TAG = JusterLoginServiceClient.class.getName();

    private static JusterLoginServiceClient mInstance;
    private static IAPIAdjusterMate iAPIAdjusterMate;
    private static Context mContext = null;

    /**
     * Returns the instance of this singleton.
     */
    public static IAPIAdjusterMate getInstance(Context context) {
        if (mInstance == null) {
            mContext = context.getApplicationContext();
            mInstance = new JusterLoginServiceClient();
        }
        return getiAPIAdjusterMate();
    }

    /**
     * Returns the instance of this singleton.
     */
    public static IAPIAdjusterMate getInstance(Context context , boolean loginRequest) {
        if (mInstance == null) {
            mContext = context.getApplicationContext();
            mInstance = new JusterLoginServiceClient();
        }
        return getiAPIAdjusterMate();
    }

    /**
     * Private singleton constructor.
     */
    private JusterLoginServiceClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IAPIUrlBaseConstants.URL_BASE)
                .client(getHttpClient())
                .addCallAdapterFactory(new ErrorHandlingCallAdapterFactory())
                .addConverterFactory(getConverterFactory())
                .build();

        setiAPIAdjusterMate(retrofit.create(IAPIAdjusterMate.class));
    }

    private static IAPIAdjusterMate getiAPIAdjusterMate() {
        return iAPIAdjusterMate;
    }

    private static void setiAPIAdjusterMate(IAPIAdjusterMate iAPIAdjusterMate) {
        JusterLoginServiceClient.iAPIAdjusterMate = iAPIAdjusterMate;
    }


    /**
     * Creates the Converter factory by setting custom HttpClient.
     */
    private Converter.Factory getConverterFactory() {
        return GsonConverterFactory.create();
    }


    /**
     * Custom Http Client to define connection timeouts.
     */
    private OkHttpClient getHttpClient() {

        OkHttpClient client = new OkHttpClient();

        client.setReadTimeout(IAPIConstants.READ_TIME_OUT, TimeUnit.MINUTES);
        client.setWriteTimeout(IAPIConstants.WRITE_TIME_OUT, TimeUnit.MINUTES);
        client.setConnectTimeout(IAPIConstants.CONNECTION_TIME_OUT, TimeUnit.MINUTES);

        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();

                Response response = chain.proceed(requestBuilder.build());
                LoggerUtils.info(TAG, "getHttpClient::: original.urlString()=" + original.urlString());

                return response;
            }
        });

        //add logging as last interceptor
        if (LoggerUtils.DEBUG_MODE) {
            LoggerUtils.info(TAG, "getHttpClient::: enabling networkInterceptors in debug mode");
            client.networkInterceptors().add(getLoggingInterceptor());// <-- this is the important line!
        }
        return client;
    }

    /**
     * Custom Logging Interceptor for Logs
     */
    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
