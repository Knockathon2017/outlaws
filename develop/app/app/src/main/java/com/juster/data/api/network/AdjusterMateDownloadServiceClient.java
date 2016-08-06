package com.juster.data.api.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * Entry point for all requests to **justerServiceClient** API.
 * Uses Retrofit library to abstract the actual REST API into a service.
 */

public class AdjusterMateDownloadServiceClient {

    public static String TAG = AdjusterMateDownloadServiceClient.class.getName();

    private static AdjusterMateDownloadServiceClient mInstance;
    private static OkHttpClient mOkHttpClient;

    /**
     * Returns the instance of this singleton.
     */
    private static AdjusterMateDownloadServiceClient getInstance() {
        if (mInstance == null) {
            mInstance = new AdjusterMateDownloadServiceClient();
        }
        return mInstance;
    }

    public static OkHttpClient getOkHttpClient() {
        return AdjusterMateDownloadServiceClient.getInstance().getHttpClient();
    }

    /**
     * Private singleton constructor.
     */
    private AdjusterMateDownloadServiceClient() {
        mOkHttpClient = getHttpClient();
    }


    /**
     * Custom Http Client to define connection timeouts.
     */
    private OkHttpClient getHttpClient() {

        if (null != mOkHttpClient)
            return mOkHttpClient;

        OkHttpClient client = new OkHttpClient();

        client.setReadTimeout(IAPIConstants.READ_TIME_OUT, TimeUnit.MINUTES);
        client.setWriteTimeout(IAPIConstants.WRITE_TIME_OUT, TimeUnit.MINUTES);
        client.setConnectTimeout(IAPIConstants.CONNECTION_TIME_OUT, TimeUnit.MINUTES);

        //add logging as last interceptor
        /*if (LoggerUtils.DEBUG_MODE) {
            LoggerUtils.info(TAG, "getHttpClient::: enabling networkInterceptors in debug mode");
            client.networkInterceptors().add(getLoggingInterceptor());// <-- this is the important line!
        }*/
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
