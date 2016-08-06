package com.juster.data.api.database.rawmodel;

/**
 * Created by deepakj on 3/8/16.
 */
public class ContactModel {

    String email_address;
    String phone_number;

    public ContactModel(String email_address , String phone_number){
        this.email_address = email_address;
        this.phone_number = phone_number;
    }
}
