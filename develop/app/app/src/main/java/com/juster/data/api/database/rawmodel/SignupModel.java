package com.juster.data.api.database.rawmodel;

/**
 * Created by deepakj on 3/8/16.
 */
public class SignupModel {
    String email_address;
    String password;

    public SignupModel(String email_address , String password){
        this.email_address = email_address;
        this.password = password;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
