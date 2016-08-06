package com.juster.data.api.database.rawmodel;

/**
 * Created by deepakj on 3/8/16.
 */
public class VerifyModel {
    String email;
    long pin;

    public VerifyModel(String email , long pin){
        this.email = email;
        this.pin = pin;
    }
}
