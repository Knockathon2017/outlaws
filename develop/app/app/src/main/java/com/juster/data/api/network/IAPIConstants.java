package com.juster.data.api.network;

public interface IAPIConstants {

    int READ_TIME_OUT = 3;
    int WRITE_TIME_OUT = 3;
    int CONNECTION_TIME_OUT = 3;

    /*****
     * For Crashlytics Environment Log
     ****/
    String KEY_ENVIRONMENT = "environment";

    /*****
     * Header Starts
     ****/
    String HEADER_DEVICE_ID = "deviceId";
    String HEADER_STOKEN = "Authorization";
    String HEADER_RCODE = "code";
    String HEADER_PERMISSION_HASH = "PermissionHash";
    /*****
     * Header Ends
     ****/

    String KEY_MULTIPART_FORM_DATA = "multipart/form-data";
}
