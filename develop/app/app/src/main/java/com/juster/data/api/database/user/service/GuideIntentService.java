package com.juster.data.api.database.user.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.juster.data.LocalSyncBroadcast;
import com.juster.data.api.database.rawmodel.GuideRespose;
import com.juster.data.api.database.rawmodel.UserLoginResponse;
import com.juster.data.api.database.user.controller.GuideQuery;
import com.juster.data.api.database.user.model.GuidesDetail;
import com.juster.data.api.network.IAPIAdjusterMateWrapper;
import com.juster.data.api.network.custom.IMyCallBack;
import com.juster.logger.LoggerUtils;
import com.juster.prefs.PreferenceManagerSingleton;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Response;

/**
 * Created by deepakj on 6/8/16.
 */
public class GuideIntentService extends IntentService {

    public static final String TAG = UserIntentService.class.getSimpleName();
    public static final String RESULT_TYPE = "result_type";
    public static final String USER_BUNDLE_DATA = "user_bundle_data";

    public static final String KEY_BUNDLE_USER_EMAIL = "USER_EMAIL";
    public static final String KEY_BUNDLE_USER_PASSWORD = "USER_PASSWORD";
    public static final String KEY_BUNDLE_VERIFY_PIN = "USER_SMS_VERIFY_PIN";
    public static final String KEY_BUNDLE_PHONE_NO = "USER_SMS_PHONE_NO";

    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_NO_CLAIM = 2;

    LocalSyncBroadcast mSyncBroadCast;
    public Intent intentResult = new Intent();

    public static IntentFilter getServiceIntentFilters(){
        IntentFilter filter=new IntentFilter();
        filter.addAction(IActivityActions.ACTION_LIST);
        filter.addAction(IActivityActions.ACTION_SEND_STATUS);
        return filter;
    }

    public interface IActivityActions {
        String ACTION_LIST =  "com.juster.data.api.database.user.service" +
                ".ACTION_LIST";
        String ACTION_SEND_STATUS =  "com.juster.data.api.database.user.service" +
                ".ACTION_SEND_STATUS";
    }

    public GuideIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mSyncBroadCast = new LocalSyncBroadcast(this);
        Bundle data = intent.getBundleExtra(USER_BUNDLE_DATA);

        LoggerUtils.info(TAG, "onHandleIntent:::::");
        String action = intent.getAction();
        if (action == null) return;

        if(action.equals(IActivityActions.ACTION_SEND_STATUS)){
            changeAvailability();
            mSyncBroadCast.broadcastIntentWithAction(IActivityActions.ACTION_SEND_STATUS, intentResult);
        }

        if(action.equals(IActivityActions.ACTION_LIST)) {
            getListServiceCall();
            mSyncBroadCast.broadcastIntentWithAction(IActivityActions.ACTION_LIST, intentResult);
        }
    }

    private void getListServiceCall() {

        String Languages = "English,Hindi";
        String Location = PreferenceManagerSingleton.getInstance(this).getTempSearchLocation();
        int Rating = 0;

        IAPIAdjusterMateWrapper.postGuidesData(Languages, Location, Rating,
                GuideIntentService.this).execute(new IMyCallBack<GuideRespose>() {

            @Override
            public void success(final Response<GuideRespose> response) {
                GuideRespose guideRespose = response.body();
                ArrayList<GuidesDetail> guidesDetails = guideRespose.getGuidesDetail();
                GuideQuery hintListQuery = new GuideQuery(GuideIntentService.this);
                LoggerUtils.info(TAG , "getCount:::"+hintListQuery.getCount());
                int resultSuccess;
                resultSuccess = hintListQuery.updateUserInfo(guidesDetails);
                if(resultSuccess == GuideQuery.IQueryStatus.SUCCESS) {
                    intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_SUCCESS);
                } else {
                    intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_NO_CLAIM);
                }
                LoggerUtils.info(TAG , "getCount:::"+hintListQuery.getCount());
                intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_SUCCESS);
            }

            @Override
            public void serverFailure(Response<GuideRespose> response) {
                intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
            }

            @Override
            public void unauthenticated(Response<GuideRespose> response) {
                intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
            }

            @Override
            public void serverDown(Response<GuideRespose> response) {
                intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
            }

            @Override
            public void userBlock(Response<GuideRespose> response) {
                intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
            }

            @Override
            public void networkError(IOException e) {
                intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
            }

            @Override
            public void unexpectedError(Throwable t) {
                intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
            }
        });
    }

    private void changeAvailability() {

        String isavailable = ""+PreferenceManagerSingleton.getInstance(this).getTempguideisAvailable();
        String contact = PreferenceManagerSingleton.getInstance(this).getTempContactNo();

        IAPIAdjusterMateWrapper.postAvailability(contact, isavailable, GuideIntentService.this)
                .execute(new IMyCallBack<UserLoginResponse>() {

                    @Override
                    public void success(final Response<UserLoginResponse> response) {
                        LoggerUtils.info(TAG , "changeAvailability:::");
                        intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_SUCCESS);
                    }

                    @Override
                    public void serverFailure(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unauthenticated(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void serverDown(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void userBlock(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void networkError(IOException e) {
                        intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unexpectedError(Throwable t) {
                        intentResult.putExtra(RESULT_TYPE, GuideIntentService.RESULT_FAILURE);
                    }
                });
    }
}