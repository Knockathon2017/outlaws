package com.juster.data.api.network;

import android.content.Context;

import com.juster.data.api.database.rawmodel.ContactModel;
import com.juster.data.api.database.rawmodel.GuideRespose;
import com.juster.data.api.database.rawmodel.SmsNewResponse;
import com.juster.data.api.database.rawmodel.UserLoginResponse;
import com.juster.data.api.database.rawmodel.VerifyModel;
import com.juster.data.api.network.custom.IMyCall;

/**
 * Created by Anurag Singh on 15/12/15 1:29 PM.
 */
public class IAPIAdjusterMateWrapper {

    public static IMyCall<UserLoginResponse> postLogin(Context context ,String UserName, String
            password, String isguide) {
        return justerServiceClient.getInstance(context).postLogin(
                password, UserName, isguide);
    }
    public static IMyCall<UserLoginResponse> postSignUp(Context context , String contact, String
            password , String name, String licence, String isguide) {
        return justerServiceClient.getInstance(context).postSignUp(contact, password, name,
                licence, isguide);
    }

    public static IMyCall<SmsNewResponse> postSmsNew(String userEmail, String
            phone_number, Context context) {
        return justerServiceClient.getInstance(context).postSmsNew(new ContactModel
                (userEmail, phone_number));
    }

    public static IMyCall<GuideRespose> postGuidesData(String Languages, String
            Location, int Rating, Context context) {
        return justerServiceClient.getInstance(context).postGuidelist(Languages, Location, Rating);
    }

    public static IMyCall<UserLoginResponse> postAvailability(String MobileNumber, String
            IsAvailable, Context context) {
        return justerServiceClient.getInstance(context).postAvailability(MobileNumber, IsAvailable);
    }

    public static IMyCall<UserLoginResponse> postVerifyMobile(String userEmail, long
            pin , Context context) {
        return justerServiceClient.getInstance(context).postVerifyMobile(new VerifyModel
                (userEmail, pin));
    }
}