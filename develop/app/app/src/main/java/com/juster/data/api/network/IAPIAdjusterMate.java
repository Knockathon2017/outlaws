package com.juster.data.api.network;

import com.juster.data.api.database.rawmodel.ContactModel;
import com.juster.data.api.database.rawmodel.GuideRespose;
import com.juster.data.api.database.rawmodel.SmsNewResponse;
import com.juster.data.api.database.rawmodel.UserLoginResponse;
import com.juster.data.api.database.rawmodel.VerifyModel;
import com.juster.data.api.network.custom.IMyCall;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Anurag Singh on 15/12/15 1:29 PM.
 */
public interface IAPIAdjusterMate {

    @FormUrlEncoded
    @POST("/login")
    IMyCall<UserLoginResponse> postLogin(@Field("Password") String password,
                                         @Field("UserName") String UserName,
                                         @Field("IsGuide") String isGuide);

    @FormUrlEncoded
    @POST("/signup")
    IMyCall<UserLoginResponse> postSignUp(@Field("MobileNumber") String contact,
                                          @Field("Password") String password,
                                          @Field("Name") String name,
                                          @Field("LicenceNo") String licence,
                                          @Field("IsGuide") String isguide);

    @POST("/v1/sms/verify")
    IMyCall<UserLoginResponse> postVerifyMobile(@Body VerifyModel verifyModel);

    @POST("/v1/sms/new")
    IMyCall<SmsNewResponse> postSmsNew(@Body ContactModel contactModel);

    @FormUrlEncoded
    @POST("/GetGuides")
    IMyCall<GuideRespose> postGuidelist(@Field("Languages") String Languages,
                               @Field("Location") String Location,
                               @Field("Rating") int Rating);

    @FormUrlEncoded
    @POST("/ChangeGuideAvailability")
    IMyCall<UserLoginResponse> postAvailability(@Field("MobileNumber") String MobileNumber,
                                        @Field("IsAvailable") String IsAvailable);
}
