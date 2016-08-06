package com.juster.data.api.database.user.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.juster.data.LocalSyncBroadcast;
import com.juster.data.api.database.rawmodel.SmsNewResponse;
import com.juster.data.api.database.rawmodel.UserLoginResponse;
import com.juster.data.api.network.IAPIAdjusterMateWrapper;
import com.juster.data.api.network.custom.IMyCallBack;
import com.juster.logger.LoggerUtils;
import com.juster.prefs.PreferenceManagerSingleton;

import java.io.IOException;

import retrofit.Response;

/**
 * Created by deepakj on 2/8/16.
 */

public class UserIntentService extends IntentService {

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
        filter.addAction(IActivityActions.ACTION_SIGNUP);
        filter.addAction(IActivityActions.ACTION_LOGIN);
        filter.addAction(IActivityActions.ACTION_SEND_CONTACT);
        filter.addAction(IActivityActions.ACTION_VERIFY_MOBILE);
        return filter;
    }

    public interface IActivityActions {
        String ACTION_SIGNUP =  "com.juster.data.api.database.user.service" +
                ".ACTION_SIGNUP";
        String ACTION_LOGIN =  "com.juster.data.api.database.user.service" +
                ".ACTION_LOGIN";
        String ACTION_VERIFY_MOBILE =  "com.juster.data.api.database.user.service" +
                ".ACTION_VERIFY_MOBILE";
        String ACTION_SEND_CONTACT =  "com.juster.data.api.database.user.service" +
                ".ACTION_SEND_CONTACT";
    }

    public UserIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mSyncBroadCast = new LocalSyncBroadcast(this);
        Bundle data = intent.getBundleExtra(USER_BUNDLE_DATA);

        LoggerUtils.info(TAG, "onHandleIntent:::::");
        String action = intent.getAction();
        if (action == null) return;

        if(action.equals(IActivityActions.ACTION_LOGIN)){
            loginServiceCall();
            mSyncBroadCast.broadcastIntentWithAction(IActivityActions.ACTION_LOGIN, intentResult);
        }

        if(action.equals(IActivityActions.ACTION_SIGNUP)) {
            signUpServiceCall(data);
            mSyncBroadCast.broadcastIntentWithAction(IActivityActions.ACTION_SIGNUP, intentResult);
        }

        if(action.equals(IActivityActions.ACTION_SEND_CONTACT)) {
            contactSmsServiceCall(data);
            mSyncBroadCast.broadcastIntentWithAction(IActivityActions.ACTION_SEND_CONTACT, intentResult);
        }

        if(action.equals(IActivityActions.ACTION_VERIFY_MOBILE)) {
            verifyContactServiceCall(data);
            mSyncBroadCast.broadcastIntentWithAction(IActivityActions.ACTION_VERIFY_MOBILE, intentResult);
        }
    }

    // Method For fetch nearby activities from network
    private void contactSmsServiceCall(@NonNull Bundle data) {

        final String userEmail;
        final String phone_no;

        userEmail = data.getString(KEY_BUNDLE_USER_EMAIL);
        phone_no = data.getString(KEY_BUNDLE_PHONE_NO);

        IAPIAdjusterMateWrapper.postSmsNew(userEmail, phone_no, UserIntentService.this)
                .execute(new IMyCallBack<SmsNewResponse>() {

                    @Override
                    public void success(Response<SmsNewResponse> response) {
                        SmsNewResponse smsNewResponse = response.body();
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_SUCCESS);
                    }

                    @Override
                    public void serverFailure(Response<SmsNewResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unauthenticated(Response<SmsNewResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void serverDown(Response<SmsNewResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void userBlock(Response<SmsNewResponse> response) {
                        //Handle User Block scenario
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void networkError(IOException e) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unexpectedError(Throwable t) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }
                });
    }

    // Method For fetch nearby activities from network
    private void verifyContactServiceCall(@NonNull Bundle data) {

        final String userEmail;
        final long verifypin;

        userEmail = data.getString(KEY_BUNDLE_USER_EMAIL);
        verifypin = data.getLong(KEY_BUNDLE_VERIFY_PIN);

        IAPIAdjusterMateWrapper.postVerifyMobile(userEmail, verifypin, UserIntentService.this)
                .execute(new IMyCallBack<UserLoginResponse>() {

                    @Override
                    public void success(Response<UserLoginResponse> response) {
                        UserLoginResponse activityResponse = response.body();
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_SUCCESS);
                    }

                    @Override
                    public void serverFailure(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unauthenticated(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void serverDown(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void userBlock(Response<UserLoginResponse> response) {
                        //Handle User Block scenario
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void networkError(IOException e) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unexpectedError(Throwable t) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }
                });
    }

    // Method For fetch nearby activities from network
    private void signUpServiceCall(@NonNull Bundle data) {

        String tempContact = PreferenceManagerSingleton.getInstance(this).getTempContactNo();
        String temppassword = PreferenceManagerSingleton.getInstance(this).getTempPassword();
        String tempisGuide = ""+PreferenceManagerSingleton.getInstance(this).getTempisGuide();
        String tempName = PreferenceManagerSingleton.getInstance(this).getTempUserName();
        String tempLicence = PreferenceManagerSingleton.getInstance(this).getTempLicenceNo();

        IAPIAdjusterMateWrapper.postSignUp(UserIntentService.this,tempContact, temppassword ,
                tempName,tempLicence,tempisGuide)
                .execute(new IMyCallBack<UserLoginResponse>() {

                    @Override
                    public void success(Response<UserLoginResponse> response) {
                        UserLoginResponse activityResponse = response.body();
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_SUCCESS);
                    }

                    @Override
                    public void serverFailure(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unauthenticated(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void serverDown(Response<UserLoginResponse> response) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void userBlock(Response<UserLoginResponse> response) {
                        //Handle User Block scenario
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void networkError(IOException e) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }

                    @Override
                    public void unexpectedError(Throwable t) {
                        intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
                    }
                });
    }

    private void loginServiceCall() {

        String tempContact = PreferenceManagerSingleton.getInstance(this).getTempContactNo();
        String temppassword = PreferenceManagerSingleton.getInstance(this).getTempPassword();
        String tempisGuide = ""+PreferenceManagerSingleton.getInstance(this).getTempisGuide();

        IAPIAdjusterMateWrapper.postLogin(UserIntentService.this, tempContact, temppassword,
                tempisGuide).execute(new IMyCallBack<UserLoginResponse>() {

            @Override
            public void success(final Response<UserLoginResponse> response) {
                intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_SUCCESS);
            }

            @Override
            public void serverFailure(Response<UserLoginResponse> response) {
                intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
            }

            @Override
            public void unauthenticated(Response<UserLoginResponse> response) {
                intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
            }

            @Override
            public void serverDown(Response<UserLoginResponse> response) {
                intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
            }

            @Override
            public void userBlock(Response<UserLoginResponse> response) {
                intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
            }

            @Override
            public void networkError(IOException e) {
                intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
            }

            @Override
            public void unexpectedError(Throwable t) {
                intentResult.putExtra(RESULT_TYPE, UserIntentService.RESULT_FAILURE);
            }
        });
    }
}
